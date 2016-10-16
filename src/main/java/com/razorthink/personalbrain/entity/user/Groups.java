package com.razorthink.personalbrain.entity.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "groups", catalog = "pbrain" )
public class Groups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "group_id", nullable = false )
    private Integer groupId;

    @Column( name = "group_name", nullable = false, length = 45 )
    private String groupName;

    @Column( name = "group_created_by_frn_user_id", nullable = false )
    private Integer groupCreatedByFrnUserId;

    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "group_created_dttm", nullable = false )
    private Date groupCreatedDttm;

    @Column( name = "group_active", nullable = false )
    private Boolean groupActive;

    @OneToMany( mappedBy = "groups", targetEntity = UserGroups.class )
    private List<UserGroups> listOfUserGroups;

    public Groups()
    {
        super();
    }

    public Integer getGroupId()
    {
        return this.groupId;
    }

    public void setGroupId( Integer groupId )
    {
        this.groupId = groupId;
    }

    public String getGroupName()
    {
        return this.groupName;
    }

    public void setGroupName( String groupName )
    {
        this.groupName = groupName;
    }

    public Integer getGroupCreatedByFrnUserId()
    {
        return this.groupCreatedByFrnUserId;
    }

    public void setGroupCreatedByFrnUserId( Integer groupCreatedByFrnUserId )
    {
        this.groupCreatedByFrnUserId = groupCreatedByFrnUserId;
    }

    public Date getGroupCreatedDttm()
    {
        return this.groupCreatedDttm;
    }

    public void setGroupCreatedDttm( Date groupCreatedDttm )
    {
        this.groupCreatedDttm = groupCreatedDttm;
    }

    public Boolean getGroupActive()
    {
        return this.groupActive;
    }

    public void setGroupActive( Boolean groupActive )
    {
        this.groupActive = groupActive;
    }

    public List<UserGroups> getListOfUserGroups()
    {
        return this.listOfUserGroups;
    }

    public void setListOfUserGroups( List<UserGroups> listOfUserGroups )
    {
        this.listOfUserGroups = listOfUserGroups;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(groupId);
        sb.append("]:");
        sb.append(groupName);
        sb.append("|");
        sb.append(groupCreatedByFrnUserId);
        sb.append("|");
        sb.append(groupCreatedDttm);
        sb.append("|");
        sb.append(groupActive);
        return sb.toString();
    }

}

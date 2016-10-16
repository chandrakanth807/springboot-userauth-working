package com.razorthink.personalbrain.entity.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "users", catalog = "pbrain" )
@NamedQueries( { @NamedQuery( name = "Users.countAll", query = "SELECT COUNT(x) FROM Users x" ) } )
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "user_id", nullable = false )
    private Integer userId;

    @Column( name = "user_name", nullable = false, length = 45 )
    private String userName;

    @Column( name = "user_password", nullable = false, length = 45 )
    private String userPassword;

    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "user_created_dttm", nullable = false )
    private Date userCreatedDttm;

    @Column( name = "user_email", nullable = false, length = 150 )
    private String userEmail;

    @Column( name = "user_active", nullable = false )
    private Boolean userActive;

    @OneToMany( mappedBy = "users", targetEntity = UserGroups.class )
    private List<UserGroups> listOfUserGroups;

    @OneToMany( mappedBy = "users", targetEntity = UserRoles.class )
    private List<UserRoles> listOfUserRoles;

    public Users()
    {
        super();
    }

    public Integer getUserId()
    {
        return this.userId;
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getUserPassword()
    {
        return this.userPassword;
    }

    public void setUserPassword( String userPassword )
    {
        this.userPassword = userPassword;
    }

    public Date getUserCreatedDttm()
    {
        return this.userCreatedDttm;
    }

    public void setUserCreatedDttm( Date userCreatedDttm )
    {
        this.userCreatedDttm = userCreatedDttm;
    }

    public String getUserEmail()
    {
        return this.userEmail;
    }

    public void setUserEmail( String userEmail )
    {
        this.userEmail = userEmail;
    }

    public Boolean getUserActive()
    {
        return this.userActive;
    }

    public void setUserActive( Boolean userActive )
    {
        this.userActive = userActive;
    }

    public List<UserGroups> getListOfUserGroups()
    {
        return this.listOfUserGroups;
    }

    public void setListOfUserGroups( List<UserGroups> listOfUserGroups )
    {
        this.listOfUserGroups = listOfUserGroups;
    }

    public List<UserRoles> getListOfUserRoles()
    {
        return this.listOfUserRoles;
    }

    public void setListOfUserRoles( List<UserRoles> listOfUserRoles )
    {
        this.listOfUserRoles = listOfUserRoles;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(userId);
        sb.append("]:");
        sb.append(userName);
        sb.append("|");
        sb.append(userPassword);
        sb.append("|");
        sb.append(userCreatedDttm);
        sb.append("|");
        sb.append(userEmail);
        sb.append("|");
        sb.append(userActive);
        return sb.toString();
    }

}

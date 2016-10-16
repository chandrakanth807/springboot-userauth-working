package com.razorthink.personalbrain.entity.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "user_groups", catalog = "pbrain" )
public class UserGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "user_group_id", nullable = false )
    private Integer id;

    @ManyToOne
    @JoinColumn( name = "user_group_frn_group_id", referencedColumnName = "group_id" )
    private Groups groups;

    @ManyToOne
    @JoinColumn( name = "user_group_frn_user_id", referencedColumnName = "user_id" )
    private Users users;

    public UserGroups()
    {
        super();
    }

    public Integer getId()
    {
        return this.id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Groups getGroups()
    {
        return this.groups;
    }

    public void setGroups( Groups groups )
    {
        this.groups = groups;
    }

    public Users getUsers()
    {
        return this.users;
    }

    public void setUsers( Users users )
    {
        this.users = users;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(id);
        sb.append("]:");
        return sb.toString();
    }

}

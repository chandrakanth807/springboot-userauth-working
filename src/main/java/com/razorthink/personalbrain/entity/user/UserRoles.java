package com.razorthink.personalbrain.entity.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "user_roles", catalog = "pbrain" )
public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "user_role_id", nullable = false )
    private Integer userRoleId;

    @ManyToOne
    @JoinColumn( name = "user_roles_frn_user_id", referencedColumnName = "user_id" )
    private Users users;

    @ManyToOne
    @JoinColumn( name = "user_role_frn_role_id", referencedColumnName = "role_id" )
    private Roles roles;

    public UserRoles()
    {
        super();
    }

    public Integer getUserRoleId()
    {
        return this.userRoleId;
    }

    public void setUserRoleId( Integer userRoleId )
    {
        this.userRoleId = userRoleId;
    }

    public Users getUsers()
    {
        return this.users;
    }

    public void setUsers( Users users )
    {
        this.users = users;
    }

    public Roles getRoles()
    {
        return this.roles;
    }

    public void setRoles( Roles roles )
    {
        this.roles = roles;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(userRoleId);
        sb.append("]:");
        return sb.toString();
    }

}

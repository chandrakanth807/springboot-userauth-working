package com.razorthink.personalbrain.entity.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table( name = "roles", catalog = "pbrain" )
@NamedQueries( { @NamedQuery( name = "Roles.countAll", query = "SELECT COUNT(x) FROM Roles x" ) } )
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "role_id", nullable = false )
    private Integer id;

    @Column( name = "role_name", nullable = false, length = 45 )
    private String name;

    @OneToMany( mappedBy = "roles", targetEntity = UserRoles.class )
    private List<UserRoles> listOfUserRoles;

    public Roles()
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

    public String getName()
    {
        return this.name;
    }

    public void setName( String name )
    {
        this.name = name;
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
        sb.append(id);
        sb.append("]:");
        sb.append(name);
        return sb.toString();
    }

}

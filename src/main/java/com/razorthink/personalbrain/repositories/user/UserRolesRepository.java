package com.razorthink.personalbrain.repositories.user;

import com.razorthink.personalbrain.entity.user.Roles;
import com.razorthink.personalbrain.entity.user.UserRoles;
import com.razorthink.personalbrain.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {

    @RestResource( rel = "byUsers", path = "byUsers" )
    List<UserRoles> findByUsers(@Param("users") Users users);

    @RestResource( rel = "byRoles", path = "byRoles" )
    List<UserRoles> findByRoles(@Param("roles") Roles roles);

}

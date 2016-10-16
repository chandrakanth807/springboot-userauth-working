package com.razorthink.personalbrain.repositories.user;


import com.razorthink.personalbrain.entity.user.Groups;
import com.razorthink.personalbrain.entity.user.UserGroups;
import com.razorthink.personalbrain.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


public interface UserGroupsRepository extends JpaRepository<UserGroups, Integer> {

    @RestResource( rel = "byGroups", path = "byGroups" )
    List<UserGroups> findByGroups(@Param("groups") Groups groups);

    @RestResource( rel = "byUsers", path = "byUsers" )
    List<UserGroups> findByUsers(@Param("users") Users users);

}

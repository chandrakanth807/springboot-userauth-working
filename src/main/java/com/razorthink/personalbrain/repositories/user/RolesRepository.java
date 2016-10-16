package com.razorthink.personalbrain.repositories.user;

import com.razorthink.personalbrain.entity.user.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

}

package com.razorthink.personalbrain.repositories.user;

import com.razorthink.personalbrain.entity.user.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Groups, Integer> {

}

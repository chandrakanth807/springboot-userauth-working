package com.razorthink.personalbrain.repositories.user;

import com.razorthink.personalbrain.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring JPA Repository for Users
 *
 *
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findByUserName(String userName);

    List<Users> findByUserId(int userId);

    Users findTopByUserId(Integer userId);
}

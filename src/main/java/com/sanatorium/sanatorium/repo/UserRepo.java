package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    User findUserByEmail(String email);

    Long removeUserById(Long id);

    User findUserById(Long id);

    List<User> findUsersByPermissionName(String name);
    List<User> findUsersByPermissionNameOrderByIdAsc(String name);

}

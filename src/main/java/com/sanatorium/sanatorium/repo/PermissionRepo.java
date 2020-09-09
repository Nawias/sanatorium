package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.Permission;
import com.sanatorium.sanatorium.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepo extends JpaRepository<Permission,Integer> {

    Permission findPermissionById(int id);

    List<Permission> findByLevelNot(int level);

    Permission findPermissionByName(String name);
}

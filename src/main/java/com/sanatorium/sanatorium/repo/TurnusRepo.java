package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.Turnus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnusRepo extends JpaRepository<Turnus, Integer> {


    List<Turnus> findAllByActive(boolean active);
    List<Turnus> findAllByActiveOrderById(boolean active);
}

package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicammentRepo extends JpaRepository<Medicament, Integer> {

    List<Medicament> findMedicamentsById(Long id);
}

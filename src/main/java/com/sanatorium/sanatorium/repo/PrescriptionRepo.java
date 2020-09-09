package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepo extends JpaRepository<Prescription,Integer> {



}

package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.Prescription;
import com.sanatorium.sanatorium.models.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepo extends JpaRepository<Prescription,Integer> {

    Prescription findByVisit(Visit savedVisit);


}

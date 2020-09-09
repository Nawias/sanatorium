package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.PatientCard;
import com.sanatorium.sanatorium.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientCardRepo extends JpaRepository<PatientCard, Integer> {

    List<PatientCard> findPatientCardsByPatientOrderByIdDesc(User patient);
}

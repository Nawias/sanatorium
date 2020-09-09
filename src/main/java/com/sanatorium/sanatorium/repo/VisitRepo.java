package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.models.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface VisitRepo extends JpaRepository<Visit,Integer> {

    Visit findVisitById(Long id);

    Long removeVisitById(Long id);

    List<Visit> findTop5ByActiveAndDoctorOrderByDateTimeAsc(boolean active, User doctor);
    List<Visit> findTop10ByActiveAndDoctorOrderByDateTimeAsc(boolean active, User doctor);
    List<Visit> findByActiveAndPatientOrderByDateTimeAsc(boolean active, User doctor);
   // List<Visit> findVisitByActive(boolean active, Date date);
}

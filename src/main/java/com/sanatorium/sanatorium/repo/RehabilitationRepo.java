package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.Rehabilitation;
import com.sanatorium.sanatorium.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface RehabilitationRepo extends JpaRepository<Rehabilitation, Integer> {


}

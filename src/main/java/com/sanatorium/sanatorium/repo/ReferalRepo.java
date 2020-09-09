package com.sanatorium.sanatorium.repo;

import com.sanatorium.sanatorium.models.Referral;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface ReferalRepo extends JpaRepository<Referral, Integer> {
}

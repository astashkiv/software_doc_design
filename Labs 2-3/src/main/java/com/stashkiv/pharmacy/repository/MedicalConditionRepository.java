package com.stashkiv.pharmacy.repository;

import com.stashkiv.pharmacy.domain.MedicalCondition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MedicalCondition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long>, JpaSpecificationExecutor<MedicalCondition> {
}

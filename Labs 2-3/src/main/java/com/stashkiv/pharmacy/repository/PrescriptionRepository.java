package com.stashkiv.pharmacy.repository;

import com.stashkiv.pharmacy.domain.Prescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Prescription entity.
 */
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long>, JpaSpecificationExecutor<Prescription> {

    @Query(value = "select distinct prescription from Prescription prescription left join fetch prescription.medicines",
        countQuery = "select count(distinct prescription) from Prescription prescription")
    Page<Prescription> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct prescription from Prescription prescription left join fetch prescription.medicines")
    List<Prescription> findAllWithEagerRelationships();

    @Query("select prescription from Prescription prescription left join fetch prescription.medicines where prescription.id =:id")
    Optional<Prescription> findOneWithEagerRelationships(@Param("id") Long id);
}

package com.graduatebackend.repository;

import com.graduatebackend.entity.FamilyCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FamilyConditionRepository extends JpaRepository<FamilyCondition, Integer> {
    /**
     * get all FamilyCondition
     *
     * @return
     */
    List<FamilyCondition> findByIsDeleteIsFalse();

    /**
     * get all
     *
     * @param pageable
     * @return
     */
    Page<FamilyCondition> findByIsDeleteIsFalse(Pageable pageable);

    /**
     * find family condition by age from and age to
     *
     * @param ageFrom
     * @param ageTo
     * @return
     */
    Optional<FamilyCondition> findFamilyConditionByAgeFromAndAgeTo(int ageFrom, int ageTo);
}

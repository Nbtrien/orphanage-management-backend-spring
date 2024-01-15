package com.graduatebackend.repository;

import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
    /**
     * find by id
     *
     * @param id
     * @return
     */
    Optional<MedicalRecord> findByMedicalRecordIdAndIsDeleteIsFalse(Integer id);

    /**
     * get by children
     *
     * @param children
     * @return
     */
    @Query("SELECT m FROM MedicalRecord m WHERE m.children = :children AND m.isDelete IS FALSE"
            + " ORDER BY m.visitDate DESC ")
    List<MedicalRecord> getByChildren(@Param("children") Children children);
}

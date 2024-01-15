package com.graduatebackend.repository;

import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.ChildrenStatus;
import com.graduatebackend.entity.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ChildrenRepository extends JpaRepository<Children, Integer> {
    /**
     * get children by id
     *
     * @param id
     * @return
     */
    Optional<Children> findByChildrenIdAndIsDeleteIsFalse(Integer id);

    /**
     * find all
     *
     * @param pageable
     * @return
     */
    Page<Children> findByIsDeleteIsFalse(Pageable pageable);

    /**
     * search children by keyword
     *
     * @param pageable
     * @param keyword
     * @return
     */
    @Query(value = "SELECT c FROM Children c " +
            "WHERE (c.childrenFullName IS NOT NULL AND LOWER(c.childrenFullName) LIKE LOWER(CONCAT('%', :keyword, " +
            "'%'))) AND c" +
            ".isDelete IS " +
            "FALSE ")
    Page<Children> searchByKeyword(Pageable pageable, @Param("keyword") String keyword);

    /**
     * Search children by multiple params
     *
     * @param name
     * @param id
     * @param minAge
     * @param maxAge
     * @return
     */
    @Query("SELECT c FROM Children c " +
            "WHERE (:name IS NULL OR c.childrenFullName LIKE %:name%) " +
            "AND (:id IS NULL OR c.childrenId = :id) " +
            "AND (:minAge IS NULL OR c.childrenDateOfBirth >= :minAge) " +
            "AND (:maxAge IS NULL OR c.childrenDateOfBirth <= :maxAge)" +
            "AND c.isDelete IS FALSE AND c.family IS NULL")
    List<Children> searchChildren(@Param("name") String name,
                                  @Param("id") Integer id,
                                  @Param("minAge") Date minAge,
                                  @Param("maxAge") Date maxAge);

    @Query("SELECT c FROM Children c " +
            "WHERE (:keyword IS NULL OR c.childrenFullName LIKE %:keyword% ) " +
            "AND (:minAge IS NULL OR c.childrenDateOfBirth >= :minAge) " +
            "AND (:maxAge IS NULL OR c.childrenDateOfBirth <= :maxAge)" +
            "AND (:orphanTypeId IS NULL OR c.orphanType.orphanTypeId = :orphanTypeId)" +
            "AND (:statusId IS NULL OR c.childrenStatus.childrenStatusId = :statusId)" +
            "AND (:minDateOfAdmission IS NULL OR c.dateOfAdmission >= :minDateOfAdmission) " +
            "AND (:maxDateOfAdmission IS NULL OR c.dateOfAdmission <= :maxDateOfAdmission) " +
            "AND c.isDelete IS FALSE")
    Page<Children> search(Pageable pageable, @Param("keyword") String keyword,
                          @Param("orphanTypeId") Integer orphanTypeId,
                          @Param("minAge") Date minAge,
                          @Param("maxAge") Date maxAge,
                          @Param("statusId") Integer statusId,
                          @Param("minDateOfAdmission") Date minDateOfAdmission,
                          @Param("maxDateOfAdmission") Date maxDateOfAdmission);

    /**
     * find by family
     *
     * @param family
     * @return
     */
    List<Children> findByFamilyAndIsDeleteIsFalse(Family family);

    /**
     * @return
     */
    @Query("SELECT c FROM Children c WHERE c.isWaitingAdoption IS TRUE AND c.childrenStatus.childrenStatusId = " +
            ":statusId AND c.isDelete IS FALSE")
    List<Children> findAllAwaitingAdoption(@Param("statusId") Integer statusId);

    /**
     * find all in care children
     *
     * @return
     */
    @Query("SELECT c FROM Children c WHERE c.childrenStatus = 1 AND c.isDelete IS FALSE")
    List<Children> findAllInCareChildren();


    /**
     * Delete children by ids
     *
     * @param ids
     */
    @Modifying
    @Transactional
    @Query("UPDATE Children c SET c.isDelete = TRUE WHERE c.childrenId in(?1)")
    void softDeleteByIds(List<Integer> ids);


    /**
     * Delete children from family
     *
     * @param ids
     */
    @Modifying
    @Transactional
    @Query("UPDATE Children c SET c.family = NULL WHERE c.childrenId in(:ids)")
    void deleteFromFamily(@Param("ids") List<Integer> ids);

    /**
     * count children
     *
     * @return
     */
    @Query("SELECT COUNT(c.childrenId) FROM Children c WHERE c.childrenStatus.childrenStatusId = 1 AND c.isDelete IS " +
            "FALSE ")
    long count();

    /**
     * count by gender
     *
     * @param gender
     * @return
     */
    @Query("SELECT COUNT(c.childrenId) FROM Children c WHERE c.childrenGender = :gender"
            + " AND c.childrenStatus.childrenStatusId = 1 AND c.isDelete IS FALSE ")
    long count(@Param("gender") int gender);

    /**
     * count by age
     *
     * @param minAge
     * @param maxAge
     * @return
     */
    @Query("SELECT COUNT(c.childrenId) FROM Children c" +
            " WHERE (c.childrenDateOfBirth <= :minAge)" +
            " AND (c.childrenDateOfBirth > :maxAge)" +
            " AND c.childrenStatus.childrenStatusId = 1" +
            " AND c.isDelete IS FALSE")
    long count(@Param("minAge") Date minAge, @Param("maxAge") Date maxAge);

    /**
     * count by status
     *
     * @param status
     * @return
     */
    @Query("SELECT COUNT(c.childrenId) FROM Children c" +
            " WHERE c.childrenStatus = :status" +
            " AND c.isDelete IS FALSE")
    long count(@Param("status") ChildrenStatus status);

    /**
     * count by family
     *
     * @param family
     * @return
     */
    @Query("SELECT COUNT(c.childrenId) FROM Children c" +
            " WHERE c.family = :family" +
            " AND c.childrenStatus.childrenStatusId = 1" +
            " AND c.isDelete IS FALSE")
    long count(@Param("family") Family family);
}

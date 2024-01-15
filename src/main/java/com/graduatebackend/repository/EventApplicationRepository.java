package com.graduatebackend.repository;

import com.graduatebackend.entity.Account;
import com.graduatebackend.entity.Event;
import com.graduatebackend.entity.EventApplication;
import com.graduatebackend.entity.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventApplicationRepository extends JpaRepository<EventApplication, Integer> {

    /**
     * update application status
     *
     * @param event
     * @param ids
     * @param status
     */
    @Transactional
    @Modifying
    @Query("UPDATE EventApplication a SET a.applicationStatus = :status"
            + " WHERE a.event = :event AND a.volunteer.volunteerId in(:ids)")
    void updateApplicationsStatus(@Param("event") Event event, @Param("ids") List<Integer> ids,
                                  @Param("status") Integer status);

    /**
     * update volunteer applications status
     *
     * @param volunteer
     * @param ids
     * @param status
     */
    @Transactional
    @Modifying
    @Query("UPDATE EventApplication a SET a.applicationStatus = :status"
            + " WHERE a.volunteer = :volunteer AND a.event.eventId in(:ids)")
    void updateApplicationsStatus(@Param("volunteer") Volunteer volunteer, @Param("ids") List<Integer> ids,
                                  @Param("status") Integer status);

    /**
     * update application status
     *
     * @param event
     * @param volunteer
     */
    @Transactional
    @Modifying
    @Query("UPDATE EventApplication a SET a.applicationStatus = :status"
            + " WHERE a.volunteer = :volunteer AND a.event = :event")
    void updateApplicationStatus(@Param("event") Event event, @Param("volunteer") Volunteer volunteer,
                                 @Param("status") Integer status);

    /**
     * find by volunteer
     *
     * @param pageable
     * @param volunteer
     * @param eventState
     * @param applicationStatus
     * @param keyword
     * @return
     */
    @Query("SELECT a FROM EventApplication a WHERE a.volunteer = :volunteer"
            + " AND (:keyword IS NULL OR a.event.title LIKE LOWER(CONCAT('%', :keyword, '%')))"
            + " AND (:eventState IS NULL OR (:eventState = 1 AND a.event.eventEndDate < current_timestamp)"
            + " OR (:eventState = 2 AND a.event.eventStartDate <= current_timestamp"
            + " AND a.event.eventEndDate >= current_timestamp)"
            + " OR (:eventState = 3 AND a.event.eventStartDate > current_timestamp))"
            + " AND (:applicationStatus IS NULL OR a.applicationStatus = :applicationStatus)"
            + " AND a.isDelete IS FALSE ")
    Page<EventApplication> findAllByVolunteer(Pageable pageable, @Param("volunteer") Volunteer volunteer,
                                              @Param("eventState") Integer eventState,
                                              @Param("applicationStatus") Integer applicationStatus, @Param("keyword")
                                              String keyword);

    @Query("SELECT a FROM EventApplication a WHERE a.volunteer.account = :account AND a.isDelete IS FALSE"
            + " AND a.event.isDelete IS FALSE ORDER BY a.createDateTime DESC")
    List<EventApplication> findByAccount(@Param("account") Account account);
}

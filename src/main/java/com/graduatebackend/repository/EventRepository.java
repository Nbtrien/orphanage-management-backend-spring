package com.graduatebackend.repository;

import com.graduatebackend.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    /**
     * find all event post in website
     *
     * @param pageable
     * @return
     */
    @Query("SELECT e FROM Event e"
            + " WHERE e.publicationStartDateTime <= CURRENT_TIMESTAMP"
            + " AND (e.publicationEndDateTime >=e.publicationEndDateTime OR e.publicationEndDateTime IS NULL)"
            + " AND e.eventEndDate >= CURRENT_TIMESTAMP AND e.isDelete IS FALSE")
    Page<Event> findAllEventPost(Pageable pageable);

    /**
     * fetch events
     *
     * @param pageable
     * @return
     */
    @Query("SELECT e FROM Event e WHERE (:keyword IS NULL OR e.title LIKE LOWER(CONCAT('%', :keyword, '%')))"
            + " AND e.isDelete IS FALSE")
    Page<Event> findAllEvent(Pageable pageable, @Param("keyword") String keyword);

    /**
     * fetch progress Event
     *
     * @param pageable
     * @return
     */
    @Query("SELECT e FROM Event e WHERE (:keyword IS NULL OR e.title LIKE LOWER(CONCAT('%', :keyword, '%')))"
            + " AND e.eventStartDate <= current_timestamp AND e.eventEndDate >= current_timestamp"
            + " AND e.isDelete IS FALSE")
    Page<Event> findAllInProgressEvent(Pageable pageable, @Param("keyword") String keyword);

    /**
     * fetch Occurred Event
     *
     * @param pageable
     * @return
     */
    @Query("SELECT e FROM Event e WHERE (:keyword IS NULL OR e.title LIKE LOWER(CONCAT('%', :keyword, '%')))"
            + " AND e.eventEndDate < current_timestamp AND e.isDelete IS FALSE")
    Page<Event> findAllOccurredEvent(Pageable pageable, @Param("keyword") String keyword);

    /**
     * fetch not Occurred Event
     *
     * @param pageable
     * @return
     */
    @Query("SELECT e FROM Event e WHERE (:keyword IS NULL OR e.title LIKE LOWER(CONCAT('%', :keyword, '%')))"
            + " AND e.eventStartDate > current_timestamp AND e.isDelete IS FALSE")
    Page<Event> findAllNotOccurredEvent(Pageable pageable, @Param("keyword") String keyword);

    /**
     * find by id
     *
     * @param id
     * @return
     */
    Optional<Event> findByEventIdAndIsDeleteIsFalse(Integer id);

    /**
     * get all
     *
     * @return
     */
    @Query("SELECT e FROM Event e WHERE e.isDelete IS FALSE ORDER BY e.eventStartDate DESC")
    List<Event> getAllEvent();
}

package com.graduatebackend.repository;

import com.graduatebackend.entity.DialogflowIntent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DialogflowIntentRepository extends JpaRepository<DialogflowIntent, Integer> {
    /**
     * find by intent name
     *
     * @param name
     * @return
     */
    Optional<DialogflowIntent> findByDisplayNameAndIsDeleteIsFalse(String name);

}

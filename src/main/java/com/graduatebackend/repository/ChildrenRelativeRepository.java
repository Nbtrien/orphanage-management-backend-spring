package com.graduatebackend.repository;

import com.graduatebackend.entity.ChildrenRelativeRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ChildrenRelativeRepository extends JpaRepository<ChildrenRelativeRelationship, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE ChildrenRelativeRelationship r SET r.isDelete = TRUE"
            + " WHERE r.children.childrenId = :childrenId AND r.relative.relativeId = :relativeId")
    void delete(@Param("childrenId") Integer childrenId, @Param("relativeId") Integer relativeId);
}

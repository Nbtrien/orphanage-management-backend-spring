package com.graduatebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * BaseEntity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public abstract class BaseEntity implements Serializable {
    @Basic
    @Column(name = "IS_DELETE")
    private boolean isDelete;
    @Basic
    @Column(name = "CREATE_DATE_TIME")
    @CreationTimestamp
    private Instant createDateTime;
    @Basic
    @Column(name = "UPDATE_DATE_TIME")
    @UpdateTimestamp
    private Instant updateDateTime;

    @PrePersist
    public void onPrePersist() {
        this.createDateTime = Instant.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updateDateTime = Instant.now();
    }
}

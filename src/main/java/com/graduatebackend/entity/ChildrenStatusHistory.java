package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "children_status_history")
public class ChildrenStatusHistory extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CHILDREN_STATUS_HISTORY_ID")
    private Integer childrenStatusHistoryId;
    @Basic
    @Column(name = "START_DATE")
    private Date startDate;
    @Basic
    @Column(name = "END_DATE")
    private Date endDate;
    @Basic
    @Column(name = "NOTE")
    private String note;
    @ManyToOne
    @JoinColumn(name = "CHILDREN_ID")
    private Children children;
    @ManyToOne
    @JoinColumn(name = "CHILDREN_STATUS_ID")
    private ChildrenStatus childrenStatus;
}

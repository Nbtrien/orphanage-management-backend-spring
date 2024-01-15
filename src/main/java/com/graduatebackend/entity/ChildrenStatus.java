package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "children_status")
public class ChildrenStatus extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CHILDREN_STATUS_ID")
    private Integer childrenStatusId;
    @Basic
    @Column(name = "CHILDREN_STATUS_NAME")
    private String childrenStatusName;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "childrenStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Children> children;
    @OneToMany(mappedBy = "childrenStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChildrenStatusHistory> childrenStatusHistories;
    @OneToMany(mappedBy = "fromStatus")
    private List<StatusTransactionOption> transactionOptions;
}

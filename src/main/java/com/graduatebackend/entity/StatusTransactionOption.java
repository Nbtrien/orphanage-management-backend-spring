package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "status_transaction_option")
public class StatusTransactionOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_TRANSACTION_OPTION_ID")
    private Integer statusTransactionOptionId;
    @ManyToOne
    @JoinColumn(name = "FROM_STATUS_ID")
    private ChildrenStatus fromStatus;
    @ManyToOne
    @JoinColumn(name = "TO_STATUS_ID")
    private ChildrenStatus toStatus;
}
package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "TOKEN_ID")
    private Integer tokenId;
    @Basic
    @Column(name = "TOKEN")
    private String token;
    @Basic
    @Column(name = "ACCOUNT_MAIL_ADDRESS")
    private String accountMailAddress;
    @Basic
    @Column(name = "EXPIRATION_DATE_TIME_START")
    private Timestamp expirationDateTimeStart;
    @Basic
    @Column(name = "EXPIRATION_DATE_TIME_END")
    private Timestamp expirationDateTimeEnd;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
}

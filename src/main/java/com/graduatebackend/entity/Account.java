package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Integer accountId;
    @Basic
    @Column(name = "ACCOUNT_MAIL_ADDRESS")
    private String accountMailAddress;
    @Basic
    @Column(name = "ACCOUNT_PASSWORD")
    private String accountPassword;
    @Basic
    @Column(name = "REGISTER_DATE_TIME")
    private Timestamp registerDateTime;
    @Basic
    @Column(name = "ACCOUNT_STATUS")
    private int accountStatus;
    @Basic
    @Column(name = "LOCK_TIME")
    private Timestamp lockTime;
    @Basic
    @Column(name = "IS_FIRST_LOGIN")
    private boolean isFirstLogin;

    @OneToOne
    @JoinColumn(name = "APPLICANT_ID")
    private Applicant applicant;

    @OneToOne
    @JoinColumn(name = "VOLUNTEER_ID")
    private Volunteer volunteer;

    @OneToOne
    @JoinColumn(name = "DONOR_ID")
    private Donor donor;

    @OneToMany(mappedBy = "account")
    private List<Token> tokens;

    @OneToMany(mappedBy = "account")
    private List<Appointment> appointments;

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountMailAddress='" + accountMailAddress + '\'' +
                ", accountPassword='" + accountPassword + '\'' +
                ", registerDateTime=" + registerDateTime +
                ", accountStatus=" + accountStatus +
                ", lockTime=" + lockTime +
                ", isFirstLogin=" + isFirstLogin +
                ", applicant=" + applicant.toString() +
                '}';
    }
}


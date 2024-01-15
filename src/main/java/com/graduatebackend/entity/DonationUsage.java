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
@Table(name = "donation_usage")
public class DonationUsage extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DONATION_USAGE_ID")
    private int donationUsageId;
    @Basic
    @Column(name = "AMOUNT")
    private double amount;
    @Basic
    @Column(name = "USAGE_TIME")
    private Timestamp usageTime;
    @Basic
    @Column(name = "BLOCKCHAIN_HASH")
    private Integer blockchainHash;
    @Basic
    @Column(name = "TRANSACTION_HASH")
    private String transactionHash;
    @Basic
    @Column(name = "DONATION_HASH")
    private String donationHash;
    @Basic
    @Column(name = "USAGE_HASH")
    private String usageHash;
    @Basic
    @Column(name = "IS_SENT_EMAIL")
    private boolean isSentEmail;
    @Basic
    @Column(name = "IS_IN_BLOCKCHAIN")
    private boolean isInBlockchain;

    @ManyToOne
    @JoinColumn(name = "DONATION_ID")
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "FUNDING_USAGE_ID")
    private FundingUsage fundingUsage;
}


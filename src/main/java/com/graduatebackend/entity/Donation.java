package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "donation")
@Entity
public class Donation extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DONATION_ID")
    private Integer donationId;
    @Basic
    @Column(name = "AMOUNT")
    private double amount;
    @Basic
    @Column(name = "USED_AMOUNT")
    private double usedAmount;
    @Basic
    @Column(name = "DONATION_TIME")
    private Timestamp donationTime;
    @Basic
    @Column(name = "BANK_CODE")
    private String bankCode;
    @Basic
    @Column(name = "BANK_TRAN_NO")
    private String bankTranNo;
    @Basic
    @Column(name = "CARD_TYPE")
    private String cardType;
    @Basic
    @Column(name = "PAY_DATE")
    private Timestamp payDate;
    @Basic
    @Column(name = "TRANSACTION_NO")
    private String transactionNo;
    @Basic
    @Column(name = "TRANSACTION_STATUS")
    private String transactionStatus;
    @Basic
    @Column(name = "PAYMENT_STATUS")
    private int paymentStatus;
    @Basic
    @Column(name = "DONATION_MESSAGE", columnDefinition = "text")
    private String donationMessage;
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
    @Column(name = "IS_USED")
    private boolean isUsed;
    @Basic
    @Column(name = "IS_SENT_EMAIL")
    private boolean isSentEmail;
    @Basic
    @Column(name = "IS_IN_BLOCKCHAIN")
    private boolean isInBlockchain;

    @ManyToOne
    @JoinColumn(name = "DONOR_ID")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "DONATION_PURPOSE_ID")
    private DonationPurpose purpose;

    @ManyToOne
    @JoinColumn(name = "FAMILY_ID")
    private Family family;

    @OneToMany(mappedBy = "donation")
    private List<DonationUsage> usages;
}

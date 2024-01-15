package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "donation_purpose")
@Entity
public class DonationPurpose extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DONATION_PURPOSE_ID")
    private Integer donationPurposeId;
    @Basic
    @Column(name = "PURPOSE")
    private String purpose;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic
    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @OneToMany(mappedBy = "purpose")
    private List<Donation> donations;

    @OneToMany(mappedBy = "purpose")
    private List<FundingUsage> fundingUsages;

    @OneToOne(mappedBy = "donationPurpose")
    private DonationPurposePost post;

    @OneToOne
    @JoinColumn(name = "INTENT_ID")
    private DialogflowIntent intent;
}

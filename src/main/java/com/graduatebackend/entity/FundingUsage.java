package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "funding_usage")
public class FundingUsage extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "FUNDING_USAGE_ID")
    private Integer fundingUsageId;
    @Basic
    @Column(name = "AMOUNT")
    private double amount;
    @Basic
    @Column(name = "USAGE_TIME")
    private Timestamp usageTime;
    @Basic
    @Column(name = "USAGE_NOTE")
    private String usageNote;

    @ManyToOne
    @JoinColumn(name = "DONATION_PURPOSE_ID")
    private DonationPurpose purpose;

    @ManyToOne
    @JoinColumn(name = "FAMILY_ID")
    private Family family;

    @OneToMany(mappedBy = "fundingUsage", cascade = CascadeType.PERSIST)
    private List<DonationUsage> donationUsages;

    public void addDonationUsages(List<DonationUsage> donationUsages) {
        if (this.donationUsages == null) {
            this.donationUsages = new ArrayList<>();
        }
        this.donationUsages.addAll(donationUsages);
        donationUsages.forEach(du -> du.setFundingUsage(this));
    }

    public void addDonationUsage(DonationUsage donationUsage) {
        if (this.donationUsages == null) {
            this.donationUsages = new ArrayList<>();
        }
        this.donationUsages.add(donationUsage);
        donationUsage.setFundingUsage(this);
    }
}

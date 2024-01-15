package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "donation_purpose_post")
@Entity
public class DonationPurposePost extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DONATION_PURPOSE_POST_ID")
    private Integer donationPurposePostId;
    @Basic
    @Column(name = "TITLE")
    private String title;
    @Basic
    @Column(name = "SUMMARY")
    private String summary;
    @Basic
    @Column(name = "CONTENT", columnDefinition = "text")
    private String content;
    @Basic
    @Column(name = "IMAGE_FILE_NAME")
    private String imageFileName;
    @Basic
    @Column(name = "IMAGE_FILE_PATH")
    private String imageFilePath;
    @Basic
    @Column(name = "BANNER_IMAGE_FILE_PATH")
    private String bannerImageFilePath;
    @Basic
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    @OneToOne
    @JoinColumn(name = "DONATION_PURPOSE_ID")
    private DonationPurpose donationPurpose;

    @Override
    public String toString() {
        return "DonationPurposePost{" +
                "donationPurposePostId=" + donationPurposePostId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", imageFileName='" + imageFileName + '\'' +
                ", imageFilePath='" + imageFilePath + '\'' +
                ", bannerImageFilePath='" + bannerImageFilePath + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

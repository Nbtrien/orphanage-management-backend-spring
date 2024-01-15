package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "family_post_image")
public class FamilyPostImage extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "FAMILY_POST_IMAGE_ID")
    private Integer familyPostImageId;
    @Basic
    @Column(name = "IMAGE_CATEGORY")
    private int imageCategory;
    @Basic
    @Column(name = "IMAGE_FILE_PATH")
    private String imageFilePath;
    @Basic
    @Column(name = "IMAGE_FILE_NAME")
    private String imageFileName;
    @ManyToOne
    @JoinColumn(name = "FAMILY_POST_ID")
    private FamilyPost familyPost;

    @Override
    public String toString() {
        return "FamilyPostImage{" +
                "familyPostImageId=" + familyPostImageId +
                ", imageCategory=" + imageCategory +
                ", imageFilePath='" + imageFilePath + '\'' +
                ", imageFileName='" + imageFileName + '\'' +
                '}';
    }
}

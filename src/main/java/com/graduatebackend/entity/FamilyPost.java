package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "family_post")
public class FamilyPost extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "FAMILY_POST_ID")
    private Integer familyPostId;
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
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    @OneToOne
    @JoinColumn(name = "FAMILY_ID")
    private Family family;

    @OneToMany(mappedBy = "familyPost", cascade = CascadeType.PERSIST)
    private List<FamilyPostImage> images;

    public void prePersist() {
        for (FamilyPostImage image : images) {
            if (image.getFamilyPost() == null) {
                image.setFamilyPost(this);
            }
        }
    }

    public void addImages(List<FamilyPostImage> images) {
        if (this.images == null) {
            this.images = new ArrayList<>();
        }
        this.images.addAll(images);
        images.forEach(img -> img.setFamilyPost(this));
    }

    public void addImage(FamilyPostImage image) {
        if (this.images == null) {
            this.images = new ArrayList<>();
        }
        this.images.add(image);
        image.setFamilyPost(this);
    }
}

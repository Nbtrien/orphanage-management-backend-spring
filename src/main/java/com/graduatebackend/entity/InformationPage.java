package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "information_page")
public class InformationPage extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "INFORMATION_PAGE_ID")
    private Integer informationPageId;
    @Basic
    @Column(name = "PAGE_TITLE")
    private String pageTitle;
    @Basic
    @Column(name = "PAGE_CONTENT")
    private String pageContent;

    @OneToOne
    @JoinColumn(name = "PAGE_TYPE_ID")
    private InformationPageType pageType;

    @Override
    public String toString() {
        return "InformationPage{" +
                "informationPageId=" + informationPageId +
                ", pageTitle='" + pageTitle + '\'' +
                ", pageContent='" + pageContent + '\'' +
                '}';
    }
}

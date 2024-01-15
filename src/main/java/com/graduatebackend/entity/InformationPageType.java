package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "information_page_type")
public class InformationPageType extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "INFORMATION_PAGE_TYPE_ID")
    private Integer pageTypeId;
    @Basic
    @Column(name = "INFORMATION_PAGE_TYPE")
    private String pageType;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne(mappedBy = "pageType")
    private InformationPage page;
}

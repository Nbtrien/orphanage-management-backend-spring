package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "website_contact")
public class WebsiteContact extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "WEBSITE_CONTACT_ID")
    private Integer websiteContactId;
    @Basic
    @Column(name = "NAME")
    private String name;
    @Basic
    @Column(name = "about")
    private String about;
    @Basic
    @Column(name = "VISION")
    private String vision;
    @Basic
    @Column(name = "MISSION")
    private String mission;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "MAIL_ADDRESS")
    private String mailAddress;
    @Basic
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Basic
    @Column(name = "LATITUDE")
    private String latitude;
    @Basic
    @Column(name = "LONGITUDE")
    private String longitude;
}

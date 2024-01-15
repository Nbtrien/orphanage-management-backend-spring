package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "faq_question")
public class FaqQuestion extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "FAQ_ID")
    private Integer faqId;
    @Basic
    @Column(name = "QUESTION")
    private String question;
    @Basic
    @Column(name = "ANSWER")
    private String answer;
}

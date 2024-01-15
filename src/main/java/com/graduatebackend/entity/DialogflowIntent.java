package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dialogflow_intent")
public class DialogflowIntent extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "INTENT_ID")
    private Integer intentId;
    @Basic
    @Column(name = "NAME")
    private String name;
    @Basic
    @Column(name = "DISPLAY_NAME")
    private String displayName;
    @Basic
    @Column(name = "RESPONSE")
    private String response;
}

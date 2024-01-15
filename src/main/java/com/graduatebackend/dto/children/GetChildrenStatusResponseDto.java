package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildrenStatusResponseDto implements Serializable {
    @JsonProperty("children_id")
    private Integer childrenId;
    @JsonProperty("children_full_name")
    private String childrenFullName;
    @JsonProperty("children_date_of_birth")
    private Date childrenDateOfBirth;
    @JsonProperty("children_gender")
    private String childrenGender;
    @JsonProperty("date_of_admission")
    private Date dateOfAdmission;
    @JsonProperty("date_of_departure")
    private Date dateOfDeparture;
    @JsonProperty("orphan_type")
    private String orphanType;
    @JsonProperty("children_status_id")
    private String childrenStatusId;
    @JsonProperty("children_status")
    private String childrenStatus;
    @JsonProperty("is_change_status")
    private boolean isChangeStatus;
    @JsonProperty("is_waiting_adoption")
    private boolean isWaitingAdoption;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("family_id")
    private Integer familyId;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("mother_id")
    private Integer motherId;
    @JsonProperty("mother_name")
    private String motherName;
}

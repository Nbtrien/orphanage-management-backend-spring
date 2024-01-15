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
public class GetChildrenFamilyHistoryResponseDto implements Serializable {
    @JsonProperty("family_history_id")
    private Integer childrenFamilyHistoryId;
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    private Date endDate;
    @JsonProperty("family_id")
    private Integer familyId;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("mother_id")
    private Integer motherId;
    @JsonProperty("mother_name")
    private String motherName;
    @JsonProperty("mother_start_date")
    private Date motherStartDate;
    @JsonProperty("mother_end_date")
    private Date motherEndDate;
}

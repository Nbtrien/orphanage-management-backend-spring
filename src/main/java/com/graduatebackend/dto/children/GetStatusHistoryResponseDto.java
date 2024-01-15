package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetStatusHistoryResponseDto implements Serializable {
    @JsonProperty("status_history_id")
    private Integer childrenStatusHistoryId;
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    private Date endDate;
    @JsonProperty("note")
    private String note;
    @JsonProperty("status_id")
    private Integer childrenStatusId;
    @JsonProperty("status_name")
    private String childrenStatusName;
    @JsonProperty("description")
    private String description;
}

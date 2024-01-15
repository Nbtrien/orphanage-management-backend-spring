package com.graduatebackend.dto.volunteer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberApplyEventRequestDto implements Serializable {
    @JsonProperty("account_id")
    private Integer accountId;
    @JsonProperty("event_id")
    private Integer eventId;
    @JsonProperty("biography")
    private String biography;
}

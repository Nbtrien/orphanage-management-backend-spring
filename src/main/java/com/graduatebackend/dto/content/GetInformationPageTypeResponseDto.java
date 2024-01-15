package com.graduatebackend.dto.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetInformationPageTypeResponseDto implements Serializable {
    @JsonProperty("page_type_id")
    private Integer pageTypeId;
    @JsonProperty("page_type")
    private String pageType;
    @JsonProperty("description")
    private String description;
    @JsonProperty("is_update")
    private boolean isUpdate;
}

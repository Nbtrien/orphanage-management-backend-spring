package com.graduatebackend.dto.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewInformationPageRequestDto implements Serializable {
    @JsonProperty("page_type_id")
    private Integer pageTypeId;
    @JsonProperty("page_title")
    private String pageTitle;
    @JsonProperty("page_content")
    private String pageContent;
}

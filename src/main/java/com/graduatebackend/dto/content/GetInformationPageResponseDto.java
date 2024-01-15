package com.graduatebackend.dto.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetInformationPageResponseDto implements Serializable {
    @JsonProperty("information_page_id")
    private Integer informationPageId;
    @JsonProperty("page_type")
    private String pageType;
    @JsonProperty("description")
    private String description;
    @JsonProperty("page_title")
    private String pageTitle;
    @JsonProperty("page_content")
    private String pageContent;
}

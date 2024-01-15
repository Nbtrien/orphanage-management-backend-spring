package com.graduatebackend.dto.volunteer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.image.AddNewImageDto;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewEventDetailRequestDto implements Serializable {
    @JsonProperty("headline")
    private String headline;
    @JsonProperty("contents")
    private String contents;
    @JsonProperty("image")
    private AddNewImageDto image;
}

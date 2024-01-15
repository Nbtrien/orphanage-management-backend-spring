package com.graduatebackend.dto.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PresignedUrlRequestDto implements Serializable {

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("folderPath")
    private String folderPath;
}

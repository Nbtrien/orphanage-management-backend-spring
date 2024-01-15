package com.graduatebackend.dto.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewFaqQuestionRequestDto implements Serializable {
    @JsonProperty("question")
    private String question;
    @JsonProperty("answer")
    private String answer;
}

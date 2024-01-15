package com.graduatebackend.dto.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFaqQuestionResponseDto implements Serializable {
    @JsonProperty("faq_id")
    private Integer faqId;
    @JsonProperty("question")
    private String question;
    @JsonProperty("answer")
    private String answer;
}

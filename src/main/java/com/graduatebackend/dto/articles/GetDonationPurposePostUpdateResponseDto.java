package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationPurposePostUpdateResponseDto implements Serializable {
    @JsonProperty("donation_purpose_id")
    private Integer donationPurposeId;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("donation_purpose_post_id")
    private Integer donationPurposePostId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("content")
    private String content;
}

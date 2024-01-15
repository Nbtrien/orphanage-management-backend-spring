package com.graduatebackend.dto.dialogflow;

import com.google.cloud.dialogflow.v2.Intent;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FulfillmentResponseDto implements Serializable {
    private String fulfillmentText;
    private List<Intent.Message> fulfillmentMessages;
}
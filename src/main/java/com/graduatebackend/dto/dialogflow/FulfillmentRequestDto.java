package com.graduatebackend.dto.dialogflow;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FulfillmentRequestDto implements Serializable {
    private UUID responseId;
    private String session;
    private QueryResult queryResult;
    private OriginalDetectIntentRequest originalDetectIntentRequest;

    @Override
    public String toString() {
        return "FulfillmentRequestDto{" +
                "responseId=" + responseId +
                ", session='" + session + '\'' +
                '}';
    }
}
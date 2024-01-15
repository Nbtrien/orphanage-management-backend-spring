package com.graduatebackend.dto.dialogflow;

import com.google.cloud.dialogflow.v2.Intent;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryResult implements Serializable {
    private String queryText;
    private Map<String, String> parameters;
    private boolean allRequiredParamsPresent;
    private String fulfillmentText;
    //    private List<FulfillmentMessage> fulfillmentMessages;
//    private List<OutputContext> outputContexts;
    private Intent intent;
    private float intentDetectionConfidence;
    private String languageCode;
    private Object diagnosticInfo;
}
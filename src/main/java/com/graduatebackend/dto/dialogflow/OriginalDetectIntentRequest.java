package com.graduatebackend.dto.dialogflow;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OriginalDetectIntentRequest implements Serializable {
    private String source;
    private String version;
    private Object payload;
}
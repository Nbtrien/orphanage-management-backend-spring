package com.graduatebackend.dto.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewWebsiteContactRequestDto implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("about")
    private String about;
    @JsonProperty("mission")
    private String mission;
    @JsonProperty("vision")
    private String vision;
    @JsonProperty("address")
    private String address;
    @JsonProperty("mail_address")
    private String mailAddress;
    @JsonProperty("phone_number")
    private String phoneNumber;
}

package com.graduatebackend.dto.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewAppointmentRequestDto implements Serializable {
    @JsonProperty("reason")
    private String appointmentReason;
    @JsonProperty("attendees")
    private int attendees;
    @JsonProperty("start_date_time")
    private Timestamp appointmentStartDateTime;
    @JsonProperty("end_date_time")
    private Timestamp appointmentEndDateTime;
}

package com.graduatebackend.dto.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAppointmentCalendarResponseDto implements Serializable {
    @JsonProperty("appointment_id")
    private Integer appointmentId;
    @JsonProperty("appointment_status")
    private int appointmentStatus;
    @JsonProperty("appointment_start_date_time")
    private Timestamp appointmentStartDateTime;
    @JsonProperty("appointment_end_date_time")
    private Timestamp appointmentEndDateTime;
    @JsonProperty("applicant_full_name")
    private String applicantFullName;
}

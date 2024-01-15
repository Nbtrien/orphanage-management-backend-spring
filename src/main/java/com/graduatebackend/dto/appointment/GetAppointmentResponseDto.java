package com.graduatebackend.dto.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAppointmentResponseDto implements Serializable {
    @JsonProperty("appointment_id")
    private Integer appointmentId;
    @JsonProperty("appointment_status")
    private int appointmentStatus;
    @JsonProperty("status")
    private String status;
    @JsonProperty("appointment_reason")
    private String appointmentReason;
    @JsonProperty("attendees")
    private int attendees;
    @JsonProperty("accept_date_time")
    private Timestamp acceptDateTime;
    @JsonProperty("appointment_start_date_time")
    private Timestamp appointmentStartDateTime;
    @JsonProperty("appointment_end_date_time")
    private Timestamp appointmentEndDateTime;
    @JsonProperty("deadline_date_time")
    private Timestamp deadlineDateTime;
    @JsonProperty("account_id")
    private Integer accountId;
    @JsonProperty("applicant_full_name")
    private String applicantFullName;
    @JsonProperty("applicant_mail_address")
    private String applicantMailAddress;
    @JsonProperty("applicant_phone_number")
    private String applicantPhoneNumber;
    @JsonProperty("address")
    private String address;
    @JsonProperty("create_date_time")
    private Instant createDateTime;

}

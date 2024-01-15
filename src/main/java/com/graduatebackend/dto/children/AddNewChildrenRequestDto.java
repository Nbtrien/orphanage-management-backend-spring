package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.dto.image.AddNewImageDto;
import com.graduatebackend.entity.Image;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewChildrenRequestDto implements Serializable {
    @NotBlank(message = "Please enter first name")
    @JsonProperty("first_name")
    private String childrenFirstName;
    @NotBlank(message = "Please enter last name")
    @JsonProperty("last_name")
    private String childrenLastName;
    @JsonProperty("date_of_birth")
    private Date childrenDateOfBirth;
    @Min(value = 0, message = "Please enter valid gender")
    @JsonProperty("gender")
    private int childrenGender;
    @JsonProperty("date_of_admission")
    private Date dateOfAdmission;
    @NotBlank(message = "Please enter ethnicity")
    @JsonProperty("ethnicity")
    private String childrenEthnicity;
    @NotBlank(message = "Please enter religion")
    @JsonProperty("religion")
    private String childrenReligion;
    @JsonProperty("circumstance")
    private String childrenCircumstance;
    @JsonProperty("address")
    private AddNewAddressDto address;
    @NotNull(message = "Please choose orphanage type")
    @JsonProperty("orphanage_type_id")
    private Integer orphanageTypeId;
    @NotNull(message = "Please choose waiting adoption status")
    @JsonProperty("is_waiting_adoption")
    private Integer isWaitingAdoption;
    @JsonProperty("image")
    private AddNewImageDto image;
    @JsonProperty("documents")
    private List<DocumentDto> documents;
}

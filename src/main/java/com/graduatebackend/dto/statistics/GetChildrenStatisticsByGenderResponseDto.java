package com.graduatebackend.dto.statistics;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildrenStatisticsByGenderResponseDto implements Serializable {
    private long male;
    private long female;
}

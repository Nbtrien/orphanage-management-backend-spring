package com.graduatebackend.dto.user;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListUserDto implements Serializable {
    List<UserDto> users;
}

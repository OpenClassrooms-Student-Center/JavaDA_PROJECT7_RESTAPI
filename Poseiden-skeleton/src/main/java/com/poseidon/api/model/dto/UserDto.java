package com.poseidon.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private Integer id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    private String password;

    @NotBlank(message = "Full name is mandatory")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    private String role;
}

package com.nnk.springboot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistration {

    @NotBlank(message = "Username is mandatory")
    private String username;

    private String password;

    @NotBlank(message = "Full name is mandatory")
    private String fullname;

}

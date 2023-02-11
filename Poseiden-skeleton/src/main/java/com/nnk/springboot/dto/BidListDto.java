package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BidListDto {

    @NotBlank
    private String account;

    @NotBlank
    private String type;

    private double bidQuantity;
}


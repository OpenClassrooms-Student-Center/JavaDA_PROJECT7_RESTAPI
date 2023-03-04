package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * The type Bid list dto.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BidListDto {

    @NotBlank
    private String account;

    @NotBlank
    private String type;

    @Positive(message = "Quantity must be positive")
    @NotNull(message = "Bid Quantity is mandatory")
    private Double bidQuantity;
}


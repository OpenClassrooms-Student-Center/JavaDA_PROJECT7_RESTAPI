package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * The type Trade dto.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TradeDto {

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @Positive(message = "Quantity must be positive")
    @NotNull(message = "Buy Quantity is mandatory")
    private Double buyQuantity;
}

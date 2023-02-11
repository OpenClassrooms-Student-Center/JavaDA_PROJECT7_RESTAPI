package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CurvePointDto {

    @Positive(message = "CurveId must be positive")
    @NotNull(message = "CurveId is mandatory")
    private Integer curveId;

    @Positive(message = "Term must be positive")
    @NotNull(message = "Term is mandatory")
    private Double term;

    @Positive(message = "Value must be positive")
    @NotNull(message = "Value is mandatory")
    private Double value;

}

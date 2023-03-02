package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * The type Rating dto.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RatingDto {

    @NotBlank(message = "MoodysRating is mandatory")
    private String moodysRating;

    @NotBlank(message = "sandPRating is mandatory")
    private String sandPRating;

    @NotBlank(message = "FitchRating is mandatory")
    private String fitchRating;

    @Positive(message = "OrderNumber must be positive")
    @NotNull(message = "OrderNumber is mandatory")
    private Integer orderNumber;
}

package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {
    public Rating(String moodys_rating, String sand_pRating, String fitch_rating, int i) {
    }
    // TODO: Map columns in data table RATING with corresponding java fields
}

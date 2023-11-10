package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "rating")
@NoArgsConstructor
public class Rating {

    // TODO: Map columns in data table RATING with corresponding java fields
    @Id
    @Column(name = "")
    private Integer id;

    private String moodysRating;

    private String sandPRating;

    private String fitchRating;

    private Integer orderNumber;

    public Rating(String moodys_rating, String sand_pRating, String fitch_rating, int i) {
    }
}

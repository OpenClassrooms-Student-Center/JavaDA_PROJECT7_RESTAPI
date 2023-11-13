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

    // TODO DONE: Map columns in data table RATING with corresponding java fields
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "moodys_rating")
    private String moodysRating;

    @Column(name = "sandprating")
    private String sandPRating;

    @Column(name = "fitch_rating")
    private String fitchRating;

    @Column(name = "order_number")
    private Integer orderNumber;

}

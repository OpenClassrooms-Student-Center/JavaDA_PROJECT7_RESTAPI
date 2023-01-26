package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;
    @Column
    String moodysRating;
    @Column
    String sandPRating;
    @Column
    String fitchRating;
    @Column
    Integer orderNumber;
    // TODO: Map columns in data table RATING with corresponding java fields
}

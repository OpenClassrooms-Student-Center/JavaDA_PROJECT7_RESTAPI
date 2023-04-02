package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "rating")
@Getter
@Setter

@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CurveId")

    Integer id;
    @Column
    String moodysRating;

    @Column
    String sandPRating;
    @Column
    String fitchRating;
    @Column
    Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber){
        this.moodysRating = moodysRating;
        this.sandPRating=sandPRating;
        this.fitchRating=fitchRating;
        this.orderNumber = orderNumber;
    }

    // TODO: Map columns in data table RATING with corresponding java fields
}

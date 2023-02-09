package com.nnk.springboot.model;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@Slf4j
@NoArgsConstructor
public class Rating {

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "moodysRating")
    String moodysRating;

    @Column(name = "sandPRating")
    String sandPRating;

    @Column(name = "fitchRating")
    String fitchRating;

    @Column(name = "orderNumber")
    Integer orderNumber;

    public Rating( String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}

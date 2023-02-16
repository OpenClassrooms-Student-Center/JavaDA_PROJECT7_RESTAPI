package com.nnk.springboot.domain;

import com.nnk.springboot.dto.RatingDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @Column(name = "moodysRating", length = 125)
    private String moodysRating;

    @Column(name = "sandPRating", length = 125)
    private String sandPRating;

    @Column(name = "fitchRating", length = 125)
    private String fitchRating;

    @Column(name = "orderNumber")
    private int orderNumber;

    public Rating(RatingDto ratingDto) {
        this.moodysRating = ratingDto.getMoodysRating();
        this.sandPRating = ratingDto.getSandPRating();
        this.fitchRating = ratingDto.getFitchRating();
        this.orderNumber = ratingDto.getOrderNumber();
    }
}

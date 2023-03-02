package com.nnk.springboot.domain;

import com.nnk.springboot.dto.RatingDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type Rating.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "moodysRating", length = 125)
    private String moodysRating;

    @Column(name = "sandPRating", length = 125)
    private String sandPRating;

    @Column(name = "fitchRating", length = 125)
    private String fitchRating;

    @Column(name = "orderNumber")
    private Integer orderNumber;

    /**
     * Instantiates a new Rating.
     *
     * @param ratingDto the rating dto
     */
    public Rating(RatingDto ratingDto) {
        this.moodysRating = ratingDto.getMoodysRating();
        this.sandPRating = ratingDto.getSandPRating();
        this.fitchRating = ratingDto.getFitchRating();
        this.orderNumber = ratingDto.getOrderNumber();
    }
}

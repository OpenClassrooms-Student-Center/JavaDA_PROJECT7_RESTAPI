package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Moody's Rating required")
    private String moodysRating;
    @NotBlank(message = "Sand Rating required")
    private String sandRating;
    @NotBlank(message = "fitch Rating required")
    private String fitchRating;
    @Min(1)
    private Integer orderNumber;

    public Rating(String moodysRating, String sandRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandRating = sandRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public Rating(String moodysRating, String sandPRating, String fitchRating, int i) {
    }

    public Rating() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandRating() {
        return sandRating;
    }

    public void setSandRating(String sandRating) {
        this.sandRating = sandRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}

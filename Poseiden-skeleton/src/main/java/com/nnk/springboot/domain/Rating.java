package com.nnk.springboot.domain;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Column(length = 125)
    private String fitchRating;
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotBlank
    @NotNull(message = "id is null")
    private Integer id;
    @Column(length = 125)
    @Size(max = 125)
    private String moodysRating;
    @Column(name = "orderNumber")

    private Integer order;
    @Column(length = 125)
    private String sandPRating;

    public Rating() {};

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer order) {
        this.fitchRating = fitchRating;
        this.moodysRating = moodysRating;
        this.order = order;
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
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

    public Integer getOrderNumber() {
        return order;
    }

    public void setOrderNumber(Integer order) {
        this.order = order;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }
}

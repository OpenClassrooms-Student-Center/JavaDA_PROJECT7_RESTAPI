package com.nnk.springboot.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

@Entity
@Table(name = "rating")
public class Rating {
    @Column(length = 125)
    @Size(max = 125, message = "fitchRating should be less than 126 characters")
    private String fitchRating;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 125)
    @Size(max = 125, message = "moodysRating should be less than 126 characters")
    private String moodysRating;

    @Column(name = "orderNumber", columnDefinition = "tinyint")
    @Min(value = -128, message = "order should be more than -129")
    @Max(value = 127, message = "order should be less than 128")
    private Integer order;

    @Column(length = 125)
    @Size(max = 125, message = "sandPRating should be less than 126 characters")
    private String sandPRating;

    public Rating() {}

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    @Override
    public String toString() {
        return "&moodysRating=" + this.moodysRating +
                "&sandPRating=" + this.sandPRating +
                "&fitchRating=" + this.fitchRating +
                "&order=" + this.order;
    }
}

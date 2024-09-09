package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "rating")
@Data
public class Rating {
    
    // TODO: Map columns in data table RATING with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ratingid;
    @NotBlank(message = "MoodysRating is mandatory")
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    @Min(0)
    private Integer orderNumber;
    public Rating() {}
    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}

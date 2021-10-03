package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {
	
	public Rating() {}
	public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber ) {
		
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
		
	}
	
    @GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
    @NotBlank(message = "MoodysRating is mandatory")
	private String moodysRating;
    @NotBlank(message = "SandPRating is mandatory")
	private String sandPRating;
    @NotBlank(message = "FitchRating is mandatory")
	private String fitchRating;
    @NotNull(message = "OrderNumber is mandatory")
    @Positive
	private Integer orderNumber;
	
    // TODO: Map columns in data table RATING with corresponding java fields
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
	public String getSandPRating() {
		return sandPRating;
	}
	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
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

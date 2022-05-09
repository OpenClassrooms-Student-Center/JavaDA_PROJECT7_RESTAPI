package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "rating")
@NoArgsConstructor
public class Rating {
	
	public Rating(String moodysrating, String sandpRating, String fitchrating, int ordernumber) {
        this.moodysRating​ = moodysrating;
        this.sandPRating​ = sandpRating;
        this.fitchRating​ = fitchrating;
        this.orderNumber​ = ordernumber;
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer Id;

	@Column(name = "moodysRating")
	private String moodysRating​;
	
	@Column(name = "sandPRating")
	private String sandPRating​;
	
	
	@Column(name = "fitchRating")
	private String fitchRating​;
	
	@Column(name = "orderNumber")
	private Integer orderNumber​;
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public void setMoodysRating(String moodysRating) {
		this.moodysRating​ = moodysRating;
	}
	
	public String getMoodysRating() {
		return moodysRating​;
	}

	public String getSandPRating() {
		return sandPRating​;
	}
	
	public void setSandPRating(String sandPRating) {
		this.sandPRating​ = sandPRating;
	}
	
	public String getFitchRating() {
		return fitchRating​;
	}
	
	public void setFitchRating(String fitchRating) {
		this.fitchRating​ = fitchRating;
	}
	
	public Integer getOrderNumber() {
		return orderNumber​;
	}
	
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber​ = orderNumber;
	}
}

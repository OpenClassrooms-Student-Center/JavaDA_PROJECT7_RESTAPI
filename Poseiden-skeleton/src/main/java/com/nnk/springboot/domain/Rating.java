package com.nnk.springboot.domain;

import javax.persistence.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Rating")
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Digits(integer = 4, fraction = 0)
	@Column(name = "Id")
	private Integer id;

	@NotBlank(message = "MoodysRating is mandatory")
	@Column(name = "moodysRating")
	private String moodysRating;

	@NotBlank(message = "SandRating is mandatory")
	@Column(name = "sandPRating")
	private String sandPRating;

	@NotBlank(message = "FitchRating is mandatory")
	@Column(name = "fitchRating")
	private String fitchRating;

	@NotNull(message = "must not be null")
	@Column(name = "orderNumber")
	private Integer orderNumber;

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
		if (orderNumber > 0) {
			this.orderNumber = orderNumber;
		} else {
			this.orderNumber = null;
		}
	}

	public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
		super();
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Rating [moodysRating=" + moodysRating + ", sandPRating=" + sandPRating + ", fitchRating=" + fitchRating
				+ ", orderNumber=" + orderNumber + "]";
	}

}

package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    @Column(name = "moodysRating")
    @Size(max = 125)
    String moodysRating;

    @Column(name = "sandPRating")
    @Size(max = 125)
    String sandPRating;

    @Column(name = "fitchRating")
    @Size(max = 125)
    String fitchRating;

    @Column(name = "orderNumber")
    @PositiveOrZero
    Integer orderNumber;

}
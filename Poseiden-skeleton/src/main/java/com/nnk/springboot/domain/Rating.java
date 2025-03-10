package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Moodys Rating is mandatory")
    @Column(name = "moodys_rating")
    private String moodysRating;

    @NotBlank(message = "Sand P Rating is mandatory")
    @Column(name = "sand_p_rating")
    private String sandPRating;

    @NotBlank(message = "Fitch Rating is mandatory")
    @Column(name = "fitch_rating")
    private String fitchRating;

    @NotNull(message = "Order Number is mandatory")
    @Column(name = "order_number")
    private Integer orderNumber;
}

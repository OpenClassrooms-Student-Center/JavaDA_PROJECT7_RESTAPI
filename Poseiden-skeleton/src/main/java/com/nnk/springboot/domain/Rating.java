package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Entity
@Table(name = "rating")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rating_id")
    private Integer rating_id;

    @Column(name = "moodys_rating")
    @NonNull
    @NotBlank(message = "moodys_rating is mandatory")
    String moodys_rating;

    @Column(name="sandprating")
    @NonNull
    @NotBlank(message = "sandprating is mandatory")
    String sandprating;

    @Column(name = "fitch_rating")
    @NonNull
    @NotBlank(message = "fitch_rating is mandatory")
    String fitch_rating;



    @Column(name="order_number")
    @NonNull
    @NotNull (message = "order_number is mandatory")
    @Positive(message = "order_number be strictly positive")
    Integer order_number;





    // TODO: Map columns in data table RATING with corresponding java fields
}

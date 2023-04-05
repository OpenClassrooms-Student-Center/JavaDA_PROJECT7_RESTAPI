package com.nnk.springboot.domain;

import jakarta.persistence.*;
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
    String moodys_rating;

    @Column(name="sandprating")
    @NonNull
    String sandprating;

    @Column(name = "fitch_rating")
    @NonNull
    String fitch_rating;



    @Column(name="order_number")
    @NonNull
    Integer order_number;





    // TODO: Map columns in data table RATING with corresponding java fields
}

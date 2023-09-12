package com.nnk.springboot.domain;

/*import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;*/

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    @Column(name = "moodys_rating")
    String moodysRating;
    @Column(name = "sand_p_rating")
    String sandPRating;
    @Column(name = "fitch_rating")
    String fitchRating;
    @Column(name = "order_number")
    Integer orderNumber;
}

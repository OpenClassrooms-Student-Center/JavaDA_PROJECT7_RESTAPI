package com.nnk.springboot.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="curve_id")
    Integer curve_id;

    @Column (name = "curve_point_id")
    @NonNull
    @NotNull(message = "curve_point_id is mandatory")
    @Positive(message = "curve_point_id must be strictly positive")
    private Integer curve_point_id;
    @Column(name = "as_of_date")
    private Timestamp as_of_date;
    @Column(name="term")
    @NonNull
    @NotNull(message = "term is mandatory")
    @Positive(message = "term must be strictly positive")
    private Double term;

    @Column(name = "value")
    @NonNull
    @NotNull(message = "value is mandatory")
    @Positive(message = "value be strictly positive")
    private  Double value;

    @Column(name = "creation_date")
    private Timestamp creation_date;

    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}

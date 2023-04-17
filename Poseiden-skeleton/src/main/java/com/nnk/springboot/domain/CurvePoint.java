package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    //@NotBlank(message = "curve_point_id is mandatory")
    private Integer curve_point_id;
    @Column(name = "as_of_date")
    private Timestamp as_of_date;
    @Column(name="term")
    @NonNull
    @NotBlank(message = "term is mandatory")
    private Double term;
    @Column(name = "value")
    @NonNull
    @NotBlank(message = "value is mandatory")
    private  Double value;

    @Column(name = "creation_date")
    private Timestamp creation_date;

    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}

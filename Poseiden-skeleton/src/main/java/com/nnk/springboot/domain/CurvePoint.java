package com.nnk.springboot.domain;

import jakarta.persistence.*;
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
    private Integer curve_point_id;
    @Column(name = "as_of_date")
    private Timestamp as_of_date;
    @Column(name="term")
    @NonNull
    private Double term;
    @Column(name = "value")
    @NonNull
    private  Double value;

    @Column(name = "creation_date")
    private Timestamp creation_date;

    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}

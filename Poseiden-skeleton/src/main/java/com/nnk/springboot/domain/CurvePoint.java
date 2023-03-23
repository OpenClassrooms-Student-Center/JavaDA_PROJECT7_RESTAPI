package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@RequiredArgsConstructor
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CurveId")
    @NonNull
    Integer curveId;
    @Column
    Timestamp asOfDate;
    @Column
    @NonNull
    Double term;
    @Column
    @NonNull
    Double value;
    @Column
    Timestamp creationDate;

    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}

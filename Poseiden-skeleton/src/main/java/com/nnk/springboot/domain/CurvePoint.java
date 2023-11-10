package com.nnk.springboot.domain;

//import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Table(name = "curvepoint")
@NoArgsConstructor
public class CurvePoint {

    // TODO : Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @Column(name = "")
    private Integer id;

    private Integer curveId;

    private Timestamp asOfDate;

    private Double term;

    private Double value;

    private Timestamp creationDate;

    public CurvePoint(int i, double v, double v1) {
    }
}

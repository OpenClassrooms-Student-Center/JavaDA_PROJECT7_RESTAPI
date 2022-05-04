package com.nnk.springboot.domain;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    @NotNull(message = "Curve Id must not be null")
    @PositiveOrZero
    @Column(name = "curveId")
    Integer curveId;

    @CreationTimestamp
    @Column(name = "asOfDate")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate asOfDate;

    @Column(name = "term")
    Double term;

    @Column(name = "value")
    Double value;

    @CreationTimestamp
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "creationDate")
    LocalDate creationDate;

}

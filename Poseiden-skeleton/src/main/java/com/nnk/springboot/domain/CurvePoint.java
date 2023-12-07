package com.nnk.springboot.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

import java.util.Date;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "CurveId")
    @PositiveOrZero(message = "curveId should be a positive number or zero")
    private Integer curveId;

    private Date asOfDate;

    private Double term;

    private Double value;

    private Date creationDate;

    public CurvePoint() {};

    public CurvePoint(int curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Double getTerm() {
        return term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "&curveId=" + this.curveId +
                "&term=" + this.term +
                "&value=" + this.value;
    }
}

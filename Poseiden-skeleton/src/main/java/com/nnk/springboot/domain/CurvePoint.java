package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;

import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "CurveId")
    private Integer curveId;

    private Timestamp asOfDate;

    private double term;

    private double value;

    @Past(message = "creationDate should be in the past")
    private Timestamp creationDate;

    public CurvePoint() {};

    public CurvePoint(int curveId, double term, double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

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

    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = asOfDate;
    }

    public double getTerm() {
        return term;
    }

    public void setTerm(double term) {
        this.term = term;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "&curveId=" + this.curveId +
                "&term=" + this.term +
                "&value=" + this.value;
    }
}

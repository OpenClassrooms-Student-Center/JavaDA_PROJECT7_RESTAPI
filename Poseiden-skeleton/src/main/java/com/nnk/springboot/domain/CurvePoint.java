package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;

import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "CurveId")
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

    public Date getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getCreationDate() {
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

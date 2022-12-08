package com.nnk.springboot.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int curvePointId;

    @NotNull(message = "CurveID is mandatory")
    @Column(name = "CurveId")
    private int curveId;

    @Column(name = "asOfDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date asOfDate;

    @Column(name = "term")
    private double term;

    @Column(name = "value")
    private double value;

    @Column(name = "creationDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    public int getCurvePointId() {
	return curvePointId;
    }

    public void setCurvePointId(int curvePointId) {
	this.curvePointId = curvePointId;
    }

    public int getCurveId() {
	return curveId;
    }

    public void setCurveId(int curveId) {
	this.curveId = curveId;
    }

    public Date getAsOfDate() {
	return asOfDate;
    }

    public void setAsOfDate(Date asOfDate) {
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

    public Date getCreationDate() {
	return creationDate;
    }

    public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
    }

}

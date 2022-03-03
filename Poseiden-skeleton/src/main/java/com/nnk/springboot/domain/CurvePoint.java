package com.nnk.springboot.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.dao.DataAccessResourceFailureException;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import java.sql.Timestamp;


@Entity
@Table(name = "CurvePoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
	@Id
	@Digits(integer = 4, fraction = 0)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@NotNull(message = "must not be null")
	@Digits(integer = 4, fraction = 0, message = "must be an integer number")
	@Column(name = "CurveId")
	private Integer curveId;
	
	@Column(name = "asOfDate")
	@Past
	private Timestamp asOfDate;
	
	@NotNull(message = "must not be null")
	@Column(name = "term")
	@Digits(integer = 4, fraction = 2, message = "must be in the form 0000.00")
	private Double term;
	
	@NotNull(message = "must not be null")
	@Column(name = "value")
	@Digits(integer = 4, fraction = 2, message = "must be in the form 0000.00")
	private Double value;
	
	@CreationTimestamp
	@Past
	@Column(name = "creationDate")
	private Timestamp creationDate;

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

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public CurvePoint(Integer curveId, Double term, Double value) {
		super();
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

	public CurvePoint() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CurvePoint [curveId=" + curveId + ", term=" + term + ", value=" + value + "]";
	}
		
}
package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "curvepoint")
@NoArgsConstructor
public class CurvePoint {
	
	public CurvePoint(Integer curveId, Double term, Double value) {
		this.curveId​ = curveId;
		this.term​ = term;
		this.value​ = value;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@NotNull
	@Min(value = 0, message = "curveId must be positive")
	@Column(name = "CurveId", nullable = false)
	private Integer curveId​;
	
	private Timestamp asOfDate​; 
	
	@NotNull
	@Min(value = 0, message = "term must be positive")
	private Double term​;
	
	@NotNull
	@Min(value = 0, message = "term must be positive")
	private Double value​;
	
	private Timestamp creationDate​; 
	
	public void setCurveId(Integer curveId) {
		this.curveId​ = curveId;
	}
	
	public Integer getCurveId() {
		return curveId​;
	}
	
	public void setTerm(Double term) {
		this.term​ = term;
	}
	
	public Double getTerm() {
		return term​;
	}
	
	public void setValue(Double value) {
		this.value​ = value;
	}
	
	public Double getValue() {
		return value​;
	}
}

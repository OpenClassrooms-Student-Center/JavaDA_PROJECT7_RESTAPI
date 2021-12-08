package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Table(name = "curvepoint")
@NoArgsConstructor
public class CurvePoint {
	
	public CurvePoint(Integer CurveId, Double term, Double value) {
		this.CurveId​ = CurveId;
		this.term​ = term;
		this.value​ = value;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@NotNull
	@Min(value = 0, message = "curveId must be positive")
	@Column(name = "CurveId", nullable = false)
	private Integer CurveId​;
	
	private Timestamp asOfDate​; 
	
	@NotNull
	@Min(value = 0, message = "term must be positive")
	private Double term​;
	
	@NotNull
	@Min(value = 0, message = "term must be positive")
	private Double value​;
	
	private Timestamp creationDate​; 
	
	public void setCurveId(Integer CurveId) {
		this.CurveId​ = CurveId;
	}
	
	public Integer getCurveId() {
		return CurveId​;
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

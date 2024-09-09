package com.nnk.springboot.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name = "curvepoint")
@Data
public class CurvePoint {
    
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int curvepointid;
    private int curveId;
    private Date asOfDate;
    @Min(0)
    @NotNull
    private double term;
    @Min(0)
    @NotNull
    private double curvepointValue;
    private Date creationDate;
    public CurvePoint() {}
    public CurvePoint(int curveId, double term, double value) {
        this.curveId = curveId;
        this.term = term;
        this.curvepointValue = value;
    }
}

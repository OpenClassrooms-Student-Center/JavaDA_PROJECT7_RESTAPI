package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    public CurvePoint(int i, double v, double v1) {
    }

    public Integer getId() {
        return null;
    }

    public int getCurveId() {
        return 0;
    }

    public void setCurveId(int i) {
    }
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}

package com.nnk.springboot.domain;

import com.nnk.springboot.dto.CurvePointDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "CurvePoint")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @Column(name = "CurveId")
    private int curveId;

    @Column(name = "asOfDate")
    private Timestamp asOfDate;

    @Column(name = "term")
    private double term;

    @Column(name = "value")
    private double value;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    public CurvePoint (CurvePointDto curvePointDto){
        this.curveId = curvePointDto.getCurveId();
        this.term = curvePointDto.getTerm();
        this.value = curvePointDto.getValue();
    }

}


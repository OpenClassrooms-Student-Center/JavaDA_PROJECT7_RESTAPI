package com.nnk.springboot.domain;

import com.nnk.springboot.dto.CurvePointDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "curvepoint")
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

    public CurvePoint(CurvePointDto curvePointDto) {
        this.curveId = curvePointDto.getCurveId();
        this.term = curvePointDto.getTerm();
        this.value = curvePointDto.getValue();
    }

}


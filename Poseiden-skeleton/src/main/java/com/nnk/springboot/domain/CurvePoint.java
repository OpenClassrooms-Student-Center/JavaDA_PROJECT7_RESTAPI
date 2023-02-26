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
@Table(name = "CurvePoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "CurveId")
    private Integer curveId;

    @Column(name = "asOfDate")
    private Timestamp asOfDate;

    @Column(name = "term")
    private Double term;

    @Column(name = "value")
    private Double value;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    public CurvePoint(CurvePointDto curvePointDto) {
        this.curveId = curvePointDto.getCurveId();
        this.term = curvePointDto.getTerm();
        this.value = curvePointDto.getValue();
    }

}


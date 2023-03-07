package com.poseidon.api.model;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "curvepoint")
@Slf4j
@NoArgsConstructor
public class CurvePoint {

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(name = "CurveId")
    Integer curveId;
    @Column(name = "term")
    Double term;
    @Column(name = "value")
    Double value;

    @Transient
    @Column(name = "asOfDate")
    private LocalDateTime asOfDate;

    @Transient
    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    public CurvePoint(Integer id, Integer curveId, Double term, Double value) {
        this.id = id;
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}

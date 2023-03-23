package com.poseidon.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "curvepoint")
@Slf4j
@NoArgsConstructor
@Getter
@Setter
public class CurvePoint {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(name = "curve_id")
    Integer curveId;
    @Column(name = "term")
    Double term;
    @Column(name = "value")
    Double value;

    @Transient
    @Column(name = "as_of_Date")
    private LocalDateTime asOfDate;

    @Transient
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public CurvePoint(Integer id, Integer curveId, Double term, Double value) {
        this.id = id;
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}

package com.poseidon.SA_EG_P7_Poseidon.dal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="curve_id")
    private Integer curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    @Column
    private Double term;

    @Column
    private Double value;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    public CurvePoint (Integer curveIdIn,
                       Double termIn,
                       Double valueIn) {
        this.setCurveId(curveIdIn);
        this.setTerm(termIn);
        this.setValue(valueIn);
    }
}

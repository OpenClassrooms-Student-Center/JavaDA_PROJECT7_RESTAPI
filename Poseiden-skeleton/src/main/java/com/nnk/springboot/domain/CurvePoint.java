package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
/*import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    @Column(name = "curve_id")
    Integer curveId;
    @Column(name = "as_of_date")
    Timestamp asOfDate;
    Double term;
    Double value;
    @Column(name = "creation_date")
    Timestamp creationDate;

    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}

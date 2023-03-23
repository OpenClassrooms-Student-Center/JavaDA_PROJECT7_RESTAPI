package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "rulename")
@Getter
@Setter
@RequiredArgsConstructor
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CurveId")
    Integer Id;

    @Column
    @NonNull
    String name;
    @Column
    @NonNull
    String description;
    @Column
    @NonNull
    String json;
    @Column
    @NonNull
    String template;
    @Column
    @NonNull
    String sqlStr;
    @Column
    @NonNull
    String sqlPart;

    /*public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart){
        this.name = name;
        this.description=description;
        this.json=json;
        this.template=template;
        this.sql
    }*/


    // TODO: Map columns in data table RULENAME with corresponding java fields
}

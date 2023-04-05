package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "rulename")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rulename_id")
    Integer rulename_id;

    @Column(name="name")
    @NonNull
    String name;
    @Column(name="description")
    @NonNull
    String description;
    @Column(name = "json")
    @NonNull
    String json;
    @Column(name="template")
    @NonNull
    String template;
    @Column(name= "sql_str")
    @NonNull
    String sql_str;
    @Column(name="sql_part")
    @NonNull
    String sql_part;

    @Column(name= "rule_name")
    Integer rule_name;

    @Column(name="rulename")
    Integer rulename;

    /*public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart){
        this.name = name;
        this.description=description;
        this.json=json;
        this.template=template;
        this.sql
    }*/


    // TODO: Map columns in data table RULENAME with corresponding java fields
}

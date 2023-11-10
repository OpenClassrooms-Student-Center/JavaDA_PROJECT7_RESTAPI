package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "rulename")
@NoArgsConstructor
public class RuleName {

    // TODO: Map columns in data table RULENAME with corresponding java fields
    @Id
    @Column(name = "")
    private Integer id;

    private String name;

    private String description;

    private String json;

    private String template;

    private String sqlStr;

    private String sqlPart;

    public RuleName(String rule_name, String description, String json, String template, String sql, String sql_part) {
    }
}

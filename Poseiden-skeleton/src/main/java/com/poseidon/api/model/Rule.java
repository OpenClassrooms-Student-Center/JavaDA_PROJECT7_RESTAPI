package com.poseidon.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Table(name = "rule")
@NoArgsConstructor
@Data
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "json")
    String json;
    @Column(name = "template")
    String template;
    @Column(name = "sql_str")
    String sqlStr;
    @Column(name = "sql_part")
    String sqlPart;

    public Rule(Long id, String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public Rule(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}

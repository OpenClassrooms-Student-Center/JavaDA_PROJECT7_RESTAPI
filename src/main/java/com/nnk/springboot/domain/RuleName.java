package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "json")
    private String json;

    @Column(name = "template")
    private String template;

    @Column(name = "sqlStr")
    private String sqlStr;

    @Column(name = "sqlPart")
    private String sqlPart;

    public RuleName() {
    }

    public RuleName(Integer id, String name, String description, String json, String template, String sqlStr,
            String sqlPart) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return this.json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSqlStr() {
        return this.sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return this.sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }

}

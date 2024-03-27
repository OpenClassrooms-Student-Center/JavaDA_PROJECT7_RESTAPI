package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @NotNull(message = "Must not be null")
    @Column(name = "name")
    private String name;
    @NotNull(message = "Must not be null")
    @Column(name = "description")
    private String description;
    @NotNull(message = "Must not be null")
    @Column(name = "json")
    private String json;
    @NotNull(message = "Must not be null")
    @Column(name = "template")
    private String template;
    @NotNull(message = "Must not be null")
    @Column(name = "sqlStr")
    private String sqlStr;
    @NotNull(message = "Must not be null")
    @Column(name = "sqlPart")
    private String sqlPart;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSql() {
        return sqlStr;
    }

    public void setSql(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }
}

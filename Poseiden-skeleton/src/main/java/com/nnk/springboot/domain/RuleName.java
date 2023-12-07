package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 125)
    @Size(max = 125, message = "name should be less than 126 characters")
    private String name;

    @Column(length = 125)
    @Size(max = 125, message = "description should be less than 126 characters")
    private String description;

    @Column(length = 125)
    @Size(max = 125, message = "json should be less than 126 characters")
    private String json;

    @Column(length = 512)
    @Size(max = 512, message = "template should be less than 513 characters")
    private String template;

    @Column(length = 125)
    @Size(max = 125, message = "sqlStr should be less than 126 characters")
    private String sqlStr;

    @Column(length = 125)
    @Size(max = 125, message = "sqlPart should be less than 126 characters")
    private String sqlPart;

    public RuleName() {}

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

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

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }

    @Override
    public String toString() {
        return "&name=" + this.name +
                "&description=" + this.description +
                "&json=" + this.json +
                "&template=" + this.template +
                "&sqlStr=" + this.sqlStr +
                "&sqlPart=" + this.sqlPart;
    }
}

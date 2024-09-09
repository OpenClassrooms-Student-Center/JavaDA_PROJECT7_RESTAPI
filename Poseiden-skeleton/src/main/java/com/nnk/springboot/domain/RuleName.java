package com.nnk.springboot.domain;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name = "rulename")
@Data
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rulenameid;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull
    private String description;
    @NotBlank(message = "Json is mandatory")
    private String json;
    @NotBlank(message = "Template is mandatory")
    private String template;
    @NotBlank(message = "Sql is mandatory")
    private String sql;
    @NotBlank(message = "SqlPart is mandatory")
    private String sqlPart;
    public RuleName() {
        
    }
    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sql = sqlStr;
        this.sqlPart = sqlPart;
    }
    
}
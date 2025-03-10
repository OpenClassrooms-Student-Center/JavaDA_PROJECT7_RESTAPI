package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "description")
    private String description;

    @NotBlank(message = "Json is mandatory")
    @Column(name = "json")
    private String json;

    @NotBlank(message = "Template is mandatory")
    @Column(name = "template")
    private String template;

    @NotBlank(message = "SQL Str is mandatory")
    @Column(name = "sql_str")
    private String sqlStr;

    @NotBlank(message = "Sql Part is mandatory")
    @Column(name = "sql_part")
    private String sqlPart;
}

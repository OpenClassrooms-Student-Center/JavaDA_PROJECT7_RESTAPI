package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "name is mandatory")
    String name;
    @Column(name="description")
    @NonNull
    @NotBlank(message = "description is mandatory")
    String description;

    @Column(name = "json")
    @NonNull
    @NotBlank(message = "json is mandatory")
    String json;
    @Column(name="template")
    @NonNull
    @NotBlank(message = "template is mandatory")
    String template;
    @Column(name= "sql_str")
    @NonNull
    @NotBlank(message = "sql-str is mandatory")
    String sql_str;
    @Column(name="sql_part")
    @NonNull
    @NotBlank(message = "sql-part is mandatory")
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

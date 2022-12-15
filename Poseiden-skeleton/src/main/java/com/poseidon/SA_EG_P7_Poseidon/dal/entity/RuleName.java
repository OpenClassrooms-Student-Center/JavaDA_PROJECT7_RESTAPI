package com.poseidon.SA_EG_P7_Poseidon.dal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String json;

    @Column
    private String template;

    @Column(name="sql_str")
    private String sqlStr;

    @Column(name="sql_part")
    private String sqlPart;

    public RuleName (String nameIn,
                     String descriptionIn,
                     String jsonIn,
                     String templateIn,
                     String sqlStrIn,
                     String sqlPartIn) {
        this.setName(nameIn);
        this.setDescription(descriptionIn);
        this.setJson(jsonIn);
        this.setTemplate(templateIn);
        this.setSqlStr(sqlStrIn);
        this.setSqlPart(sqlPartIn);
    }
}

package com.nnk.springboot.domain;

import com.nnk.springboot.dto.RuleNameDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "RuleName")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "name")
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

    public RuleName(RuleNameDto ruleNameDto) {
        this.name = ruleNameDto.getName();
        this.description = ruleNameDto.getDescription();
        this.json = ruleNameDto.getJson();
        this.template = ruleNameDto.getTemplate();
        this.sqlStr = ruleNameDto.getSqlStr();
        this.sqlPart = ruleNameDto.getSqlPart();
    }
}

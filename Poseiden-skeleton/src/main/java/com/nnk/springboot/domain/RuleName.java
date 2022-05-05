package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    @Column(name = "name")
    @Size(max = 125)
    String name;

    @Column(name = "description")
    @Size(max = 125)
    String description;

    @Column(name = "json")
    @Size(max = 125)
    String json;

    @Column(name = "template")
    @Size(max = 512)
    String template;

    @Column(name = "sqlStr")
    @Size(max = 125)
    String sqlStr;

    @Column(name = "sqlPart")
    @Size(max = 125)
    String sqlPart;

}
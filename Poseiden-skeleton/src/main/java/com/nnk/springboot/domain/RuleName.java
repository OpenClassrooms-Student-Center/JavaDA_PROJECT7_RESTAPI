package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rulename")
public class RuleName {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@NotBlank
    private String name;
	
    private String description;
    private String json;
    private String template;
    
    @Column(name = "sqlStr")
    private String sqlStr;
    
    @Column(name = "sqlPart")
    private String sqlPart;
    
    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
    	this.name = name;
    	this.description = description;
    	this.json = json;
    	this.template = template;
    	this.sqlStr = sqlStr;
    	this.sqlPart = sqlPart;
    }
}

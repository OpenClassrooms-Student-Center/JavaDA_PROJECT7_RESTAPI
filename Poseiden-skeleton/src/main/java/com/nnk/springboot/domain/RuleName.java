package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "RuleName")
public class RuleName {
    // TODO: Map columns in data table RULENAME with corresponding java fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Digits(integer = 4, fraction = 0)
	@Column(name = "Id")
	private Integer id;
	
	@NotBlank(message = "Name is mandatory")
	@Column(name = "name")
	private String name;
	
	@NotBlank(message = "Description is mandatory")
	@Column(name = "description")
	private String description;
	
	@NotBlank(message = "json is mandatory")
	@Column(name = "json")
	private String json;
	
	@NotBlank(message = "template is mandatory")
	@Column(name = "template")
	private String template;
	
	@NotBlank(message = "sql is mandatory")
	@Column(name = "sqlStr")
	private String sqlStr;
	
	@NotBlank(message = "sqlPart is mandatory")
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

	public RuleName(String name, String description, String json, String template, String sqlStr, 
			String sqlPart) {
		super();
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}

	public RuleName() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "RuleName [name=" + name + ", description=" + description + ", json=" + json + ", template=" + template
				+ ", sqlStr=" + sqlStr + ", sqlPart=" + sqlPart + "]";
	}
	
}

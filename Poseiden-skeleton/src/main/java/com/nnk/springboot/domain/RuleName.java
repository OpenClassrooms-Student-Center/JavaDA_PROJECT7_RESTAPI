package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
public class RuleName {
    public RuleName(String rule_name, String description, String json, String template, String sql, String sql_part) {
    }
    // TODO: Map columns in data table RULENAME with corresponding java fields
}

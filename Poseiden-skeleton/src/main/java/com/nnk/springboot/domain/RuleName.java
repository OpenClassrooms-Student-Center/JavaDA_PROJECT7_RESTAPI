package com.nnk.springboot.domain;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
public class RuleName {
    public RuleName(String ruleName, String description, String json, String template, String sql, String sqlPart) {
    }

    public Integer getId() {
        return null;
    }

    public Object getName() {
        return null;
    }

    public void setName(String ruleNameUpdate) {
    }
    // TODO: Map columns in data table RULENAME with corresponding java fields
}

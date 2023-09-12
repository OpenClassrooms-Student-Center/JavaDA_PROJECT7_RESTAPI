package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    String name;
    String description;
    String json;
    String template;
    @Column(name = "sql_str")
    String sqlStr;
    @Column(name = "sql_part")
    String sqlPart;
    // TODO: Map columns in data table RULENAME with corresponding java fields

}

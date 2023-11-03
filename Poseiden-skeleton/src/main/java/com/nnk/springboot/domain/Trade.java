package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Entity
@Table(name = "trade")
public class Trade {
    public Trade(String trade_account, String type) {
    }
    // TODO: Map columns in data table TRADE with corresponding java fields
}

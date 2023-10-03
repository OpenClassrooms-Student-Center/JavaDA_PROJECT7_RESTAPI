package com.nnk.springboot.domain;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Entity
@Table(name = "trade")
public class Trade {
    public Trade(String tradeAccount, String type) {
    }

    public Integer getTradeId() {
        return null;
    }

    public Object getAccount() {
        return null;
    }

    public void setAccount(String tradeAccountUpdate) {
    }
    // TODO: Map columns in data table TRADE with corresponding java fields
}

package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Table(name = "trade")
@NoArgsConstructor
public class Trade {

    // TODO: Map columns in data table TRADE with corresponding java fields
    @Id
    @Column(name = "")
    Integer tradeId;

    String account;

    String type;

    Double buyQuantity;

    Double sellQuantity;

    Double buyPrice;

    Double sellPrice;

    String benchmark;

    Timestamp tradeDate;

    String security;

    String status;

    String trader;

    String book;

    String creationName;

    Timestamp creationDate;

    String revisionName;

    Timestamp revisionDate;

    String dealName;

    String dealType;

    String sourceListId;

    String side;

    public Trade(String trade_account, String type) {
    }
}

package com.nnk.springboot.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Table(name = "trade")
@Data
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeid;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "type is mandatory")
    private String type;
    @Min(0)
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private Date tradeDate;
    private String security;
    private String status;
    private String trader;
    private String benchmark;
    private String book;
    private String creationName;
    private Date creationDate;
    private String revisionName;
    private Date revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
    public Trade() { }
    public Trade(String account, String type) {
        this.type = type;
        this.account = account;
    }
}

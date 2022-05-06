package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "tradeId")
    Integer tradeId;

    @Column(name = "account")
    @Size(max = 30)
    @NotBlank(message = "Account is mandatory")
    String account;

    @Column(name = "type")
    @Size(max = 30)
    @NotBlank(message = "Type is mandatory")
    String type;

    @PositiveOrZero
    @Column(name = "buyQuantity")
    Double buyQuantity;

    @PositiveOrZero
    @Column(name = "sellQuantity")
    Double sellQuantity;

    @PositiveOrZero
    @Column(name = "buyPrice")
    Double buyPrice;

    @PositiveOrZero
    @Column(name = "sellPrice")
    Double sellPrice;

    @Column(name = "benchmark")
    @Size(max = 125)
    String benchmark;

    @CreationTimestamp
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "tradeDate")
    LocalDate tradeDate;

    @Column(name = "security")
    @Size(max = 125)
    String security;

    @Column(name = "status")
    @Size(max = 10)
    String status;

    @Column(name = "trader")
    @Size(max = 125)
    String trader;

    @Column(name = "book")
    @Size(max = 125)
    String book;

    @Column(name = "creationName")
    @Size(max = 125)
    String creationName;

    @CreationTimestamp
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "creationDate")
    LocalDate creationDate;

    @Column(name = "revisionName")
    @Size(max = 125)
    String revisionName;

    @UpdateTimestamp
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "revisionDate")
    LocalDate revisionDate;

    @Column(name = "dealName")
    @Size(max = 125)
    String dealName;

    @Column(name = "dealType")
    @Size(max = 125)
    String dealType;

    @Column(name = "sourceListId")
    @Size(max = 125)
    String sourceListId;

    @Column(name = "side")
    @Size(max = 125)
    String side;

}
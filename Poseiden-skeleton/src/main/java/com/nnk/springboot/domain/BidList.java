package com.nnk.springboot.domain;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "BidListId")
    Integer BidListId;

    @Column(name = "account")
    @Size(max = 30)
    @NotBlank(message = "Account is mandatory")
    String account;

    @Column(name = "type")
    @Size(max = 30)
    @NotBlank(message = "Type is mandatory")
    String type;

    @PositiveOrZero
    @Column(name = "bidQuantity")
    Double bidQuantity;

    @PositiveOrZero
    @Column(name = "askQuantity")
    Double askQuantity;

    @PositiveOrZero
    @Column(name = "bid")
    Double bid;

    @PositiveOrZero
    @Column(name = "ask")
    Double ask;

    @Column(name = "benchmark")
    @Size(max = 125)
    String benchmark;

    @CreationTimestamp
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "bidListDate")
    LocalDate bidListDate;

    @Column(name = "commentary")
    @Size(max = 125)
    String commentary;

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
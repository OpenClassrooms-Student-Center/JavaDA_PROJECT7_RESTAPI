package com.nnk.springboot.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Data
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer BidListId;
    @Column(nullable = false)
    @NotBlank(message = "Username is mandatory")
    String account;
    @Column
    @NotBlank(message = "Username is mandatory")
    String type;
    @Column
    Double bidQuantity;
    @Column
    Double askQuantity;
    @Column
    Double bid;
    @Column
    Double ask;
    @Column
    String benchmark;
    @Column
    Timestamp bidListDate;
    @Column
    String commentary;
    @Column
    String security;
    @Column
    String status;
    @Column
    String trader;
    @Column
    String book;
    @Column
    String creationName;
    @Column
    Timestamp creationDate;
    @Column
    String revisionName;
    @Column
    Timestamp revisionDate;
    @Column
    String dealName;
    @Column
    String dealType;
    @Column
    String sourceListId;
    @Column
    String side;
    // TODO: Map columns in data table BIDLIST with corresponding java fields
}

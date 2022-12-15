package com.poseidon.SA_EG_P7_Poseidon.dal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bidListId;

    @Column
    private String account;

    @Column
    private String type;

    @Column(name="bid_quantity")
    private Double bidQuantity;

    @Column(name="ask_quantity")
    private Double askQuantity;

    @Column
    private Double bid;

    @Column
    private Double ask;

    @Column
    private String benchmark;

    @Column
    private Timestamp bidListDate;

    @Column
    private String commentary;

    @Column
    private String security;

    @Column
    private String status;

    @Column
    private String trader;

    @Column
    private String book;

    @Column(name="creation_name")
    private String creationName;

    @Column(name="creation_date")
    private Timestamp creationDate;

    @Column(name="revision_name")
    private String revisionName;

    @Column(name="revision_date")
    private Timestamp revisionDate;

    @Column(name="deal_name")
    private String dealName;

    @Column(name="deal_type")
    private String dealType;

    @Column(name="source_list_id")
    private String sourceListId;

    @Column
    private String side;

    public BidList (String accountIn,
                    String typeIn,
                    Double bidQuantityIn) {
        this.setAccount(accountIn);
        this.setType(typeIn);
        this.setBidQuantity(bidQuantityIn);
    }
}

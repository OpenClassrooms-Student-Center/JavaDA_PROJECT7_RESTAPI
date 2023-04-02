package com.nnk.springboot.domain;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
//@Data

@Entity
@Table(name = "bidlist")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bid_list_id")
    private Integer bidListId;
   @Column (name = "account")
   @NonNull
   String account;
   @Column (name = "type")
   @NonNull
   String type;
   @Column (name = "bidQuantity")
   @NonNull
   Double bidQuantity;
   @Column (name = "askQuantity")
   Double askQuantity;

   @Column (name = "bid")
   Double bid;
   @Column (name = "ask")
   Double ask;

   @Column (name = "benchmark")
   String benchmark;
   @Column (name = "bidListDate")
   Timestamp bidListDate;
   @Column (name = "commentary")
   String commentary;

   @Column (name = "security")
   String security;
   @Column (name = "status")
   String status;
   @Column  (name = "trader")
   String trader;
   @Column (name = "book")
   String book;
   @Column (name = "creationName")
   String creationName;
   @Column (name = "creationDate")
   Timestamp creationDate;
   @Column (name = "revisionName")
   String revisionName;

   @Column (name = "revisionDate")
   Timestamp revisionDate;

   @Column (name = "dealName")
   String dealName;

    @Column (name = "dealType")
    String dealType;
    @Column (name = "sourceListId")
    String sourceListId;
    @Column (name = "side")
    String side;

    // TODO: Map columns in data table BIDLIST with corresponding java fields
}

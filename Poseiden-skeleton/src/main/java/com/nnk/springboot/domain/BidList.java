package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    private Integer bid_list_id;
    @Column (name = "account")
    @NotBlank(message = "account is mandatory")
   @NonNull
   private String account;
   @Column (name = "type")
   @NotBlank(message = "type is mandatory")
   @NonNull
   private String type;
   @Column (name = "bid_quantity")
   //@NotNull(message = "bid_quantity is mandatory")
   @PositiveOrZero(message = "bid_quantity must be positive or zero")
   @NonNull
   private Double bid_quantity;
   @Column (name = "ask_quantity")
   private Double ask_quantity;

   @Column (name = "bid")
   private Double bid;
   @Column (name = "ask")
   private Double ask;

   @Column (name = "benchmark")
   private String benchmark;
   @Column (name = "bid_list_date")
   private Timestamp bid_list_date;
   @Column (name = "commentary")
   private String commentary;

   @Column (name = "security")
   String security;
   @Column (name = "status")
   String status;
   @Column  (name = "trader")
   String trader;
   @Column (name = "book")
   String book;
   @Column (name = "creation_name")
   String creation_name;
   @Column (name = "creation_date")
   Timestamp creation_date;
   @Column (name = "revision_name")
   String revision_name;

   @Column (name = "revision_date")
   Timestamp revision_date;

   @Column (name = "deal_name")
   String deal_name;

    @Column (name = "deal_type")
    String deal_type;
    @Column (name = "source_list_id")
    String source_list_id;
    @Column (name = "side")
    String side;

    // TODO: Map columns in data table BIDLIST with corresponding java fields
}

package com.nnk.springboot.domain;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Table(name = "trade")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TradeId")
    Integer tradeId;

    @Column
    @NonNull
    String account;
    @Column
    @NonNull
    String type;
    @Column
    Double buyQuantity;
    @Column
    Double sellQuantity;
    @Column
    Double buyPrice;
    @Column
    Double sellPrice;
    @Column
    String benchmark;
    @Column
    Timestamp tradeDate;
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

}

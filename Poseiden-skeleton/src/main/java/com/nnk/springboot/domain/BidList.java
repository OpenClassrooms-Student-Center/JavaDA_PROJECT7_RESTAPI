package com.nnk.springboot.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/**
 * The BidList class represents a bid list record in the database.
 * It contains fields for all the attributes of a bid list record.
 * Each instance of the class is a row in the bidlist table.
 *
 * @author Nguyen Ngoc Khanh
 */
@Entity
@Table(name = "bidlist")
@Data
public class BidList {
    
    /**
     * The primary key of the bid list record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bidlistid")
    private int bidlistid;
    
    /**
     * The account associated with the bid list.
     */
    @NotBlank(message = "Account is mandatory")
    private String account;
    
    /**
     * The type of the bid list.
     */
    @NotBlank(message = "type is mandatory")
    private String type;
    
    /**
     * The bid quantity associated with the bid list.
     */
    @PositiveOrZero
    private Double bidQuantity;
    
    /**
     * The ask quantity associated with the bid list.
     */
    private Double askQuantity;
    
    /**
     * The bid price associated with the bid list.
     */
    private Double bid;
    
    /**
     * The ask price associated with the bid list.
     */
    private Double ask;
    
    /**
     * The benchmark associated with the bid list.
     */
    private String benchmark;
    
    /**
     * The date of the bid list.
     */
    private Date bidListDate;
    
    /**
     * The commentary associated with the bid list.
     */
    private String commentary;
    
    /**
     * The security associated with the bid list.
     */
    private String security;
    
    /**
     * The status of the bid list.
     */
    private String status;
    
    /**
     * The trader associated with the bid list.
     */
    private String trader;
    
    /**
     * The book associated with the bid list.
     */
    private String book;
    
    /**
     * The name of the user who created the bid list.
     */
    private String creationName;
    
    /**
     * The date and time the bid list was created.
     */
    private Date creationDate;
    
    /**
     * The name of the user who last modified the bid list.
     */
    private String revisionName;
    
    /**
     * The date and time the bid list was last modified.
     */
    private Date revisionDate;
    
    /**
     * The name of the deal associated with the bid list.
     */
    private String dealName;
    
    /**
     * The type of the deal associated with the bid list.
     */
    private String dealType;
    
    /**
     * The source list ID associated with the bid list.
     */
    private String sourceListId;
    
    /**
     * The side of the bid list.
     */
    private String side;
    
    /**
     * Default constructor.
     */
    public BidList() { }
    
    /**
     * Constructor with parameters.
     * 
     * @param account the account associated with the bid list
     * @param type the type of the bid list
     * @param bidQuantity the bid quantity associated with the bid list
     */
    public BidList(String account,String type,double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}


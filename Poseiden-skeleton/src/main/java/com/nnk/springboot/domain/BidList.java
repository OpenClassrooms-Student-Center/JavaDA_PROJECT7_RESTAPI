package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import java.sql.Timestamp;

@Entity
@Table(name = "BidList")
public class BidList {
	// TODO: Map columns in data table BIDLIST with corresponding java fields
	@Id
	@Digits(integer = 4, fraction = 0)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BidListId")
	private Integer BidListId;

	@NotBlank(message = "Account is mandatory")
	@Length(max = 30, message = "must have 30 characters max")
	@Column(name = "account")
	private String account;

	@NotBlank(message = "Type is mandatory")
	@Length(max = 30, message = "must have 30 characters max")
	@Column(name = "type")
	private String type;

	@Column(name = "bidQuantity")
	@NotNull(message = "must not be null")
	@Digits(integer = 4, fraction = 2, message = "must be in the form 0000.00")
	private Double bidQuantity;

	@Column(name = "askQuantity")
	@Digits(integer = 4, fraction = 2)
	private Double askQuantity;

	@Column(name = "bid")
	@Digits(integer = 4, fraction = 2)
	private Double bid;

	@Column(name = "ask")
	@Digits(integer = 4, fraction = 2)
	private Double ask;

	@Column(name = "benchmark")
	@Length(max = 125)
	private String benchmark;

	@Column(name = "bidListDate")
	@Past
	private Timestamp bidListDate;

	@Column(name = "commentary")
	@Length(max = 125)
	private String commentary;

	@Column(name = "security")
	@Length(max = 125)
	private String security;

	@Column(name = "status")
	@Length(max = 10)
	private String status;

	@Column(name = "trader")
	@Length(max = 125)
	private String trader;

	@Column(name = "book")
	@Length(max = 125)
	private String book;

	@Column(name = "creationName")
	@Length(max = 125)
	private String creationName;

	@Column(name = "creationDate")
	@Past
	private Timestamp creationDate;

	@Column(name = "revisionName")
	@Length(max = 125)
	private String revisionName;

	@Column(name = "revisionDate")
	@Past
	private Timestamp revisionDate;

	@Column(name = "dealName")
	@Length(max = 125)
	private String dealName;

	@Column(name = "dealType")
	@Length(max = 125)
	private String dealType;

	@Column(name = "sourceListId")
	@Length(max = 125)
	private String sourceListId;

	@Column(name = "side")
	@Length(max = 125)
	private String side;

	public Integer getBidListId() {
		return BidListId;
	}

	public void setBidListId(Integer bidListId) {
		BidListId = bidListId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBidQuantity() {
		return bidQuantity;
	}

	public void setBidQuantity(Double bidQuantity) {
		if (bidQuantity > 0) {
			this.bidQuantity = bidQuantity;
		} else {
			this.bidQuantity = null;
		}
	}

	public Double getAskQuantity() {
		return askQuantity;
	}

	public void setAskQuantity(Double askQuantity) {
		this.askQuantity = askQuantity;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public Timestamp getBidListDate() {
		return bidListDate;
	}

	public void setBidListDate(Timestamp bidListDate) {
		this.bidListDate = bidListDate;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getRevisionName() {
		return revisionName;
	}

	public void setRevisionName(String revisionName) {
		this.revisionName = revisionName;
	}

	public Timestamp getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Timestamp revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getSourceListId() {
		return sourceListId;
	}

	public void setSourceListId(String sourceListId) {
		this.sourceListId = sourceListId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public BidList(String account, String type, Double bidQuantity) {
		super();
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

	public BidList() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "BidList [account=" + account + ", type=" + type + ", bidQuantity="
				+ bidQuantity + "]";
	}

}

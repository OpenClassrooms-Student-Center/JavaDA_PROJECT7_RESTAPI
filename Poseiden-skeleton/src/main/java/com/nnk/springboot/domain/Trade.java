package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.springframework.dao.DataAccessResourceFailureException;

import java.sql.Timestamp;

@Entity
@Table(name = "Trade")
public class Trade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Digits(integer = 4, fraction = 0)
	@Column(name = "tradeId")
	private Integer tradeId;

	@NotBlank(message = "Account is mandatory")
	@Length(max = 30, message = "must have 30 characters max")
	@Column(name = "account")
	private String account;

	@NotBlank(message = "Type is mandatory")
	@Length(max = 30, message = "must have 30 characters max")
	@Column(name = "type")
	private String type;

	@NotNull(message = "must not be null")
	@Digits(integer = 4, fraction = 2, message = "must be in the form 0000.00")
	@Column(name = "buyQuantity")
	private Double buyQuantity;

	@Column(name = "sellQuantity")
	private Double sellQuantity;

	@Column(name = "buyPrice")
	private Double buyPrice;

	@Column(name = "sellPrice")
	private Double sellPrice;

	@Past
	@Column(name = "tradeDate")
	private Timestamp tradeDate;

	@Length(max = 125)
	@Column(name = "security")
	private String security;

	@Length(max = 10)
	@Column(name = "status")
	private String status;

	@Length(max = 125)
	@Column(name = "trader")
	private String trader;

	@Length(max = 125)
	@Column(name = "benchmark")
	private String benchmark;

	@Length(max = 125)
	@Column(name = "book")
	private String book;

	@Length(max = 125)
	@Column(name = "creationName")
	private String creationName;

	@Past
	@Column(name = "creationDate")
	private Timestamp creationDate;

	@Length(max = 125)
	@Column(name = "revisionName")
	private String revisionName;

	@Past
	@Column(name = "revisionDate")
	private Timestamp revisionDate;

	@Length(max = 125)
	@Column(name = "dealName")
	private String dealName;

	@Length(max = 125)
	@Column(name = "dealType")
	private String dealType;

	@Length(max = 125)
	@Column(name = "sourceListId")
	private String sourceListId;

	@Length(max = 125)
	@Column(name = "side")
	private String side;

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
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

	public Double getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity) {
		if (buyQuantity > 0) {
			this.buyQuantity = buyQuantity;
		}
	}

	public Double getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(Double sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Timestamp getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Timestamp tradeDate) {
		this.tradeDate = tradeDate;
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

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
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

	public Trade(String account, String type, Double buyQuantity) {
		super();
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
	}

	public Trade() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Trade [account=" + account + ", type=" + type + ", buy quantity=" + buyQuantity + "]";
	}

}

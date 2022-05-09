package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@NoArgsConstructor
@Table(name = "trade")
@ToString
public class Trade {
	
	public Trade(String account, String type, Double buyQuantity) {
		this.account​ = account;
		this.type​ = type;
		this.buyQuantity​ = buyQuantity;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TradeId")
	private Integer TradeId;
	
	@NotEmpty
	@Column(name = "account")
	private String account​;
	
	@NotEmpty
	@Column(name = "type")
	private String type​;
	
	@Column(name = "buyQuantity")
	@NotNull
	@Min(value = 0, message = "Buy Quantity must be positive")
	private Double buyQuantity​;
	
	@Column(name = "sellQuantity")
	private Double sellQuantity​;
	
	@Column(name = "buyPrice")
	private Double buyPrice​;
	
	@Column(name = "sellPrice")
	private Double sellPrice​;
	
	@Column(name = "benchmark")
	private String benchmark​;
	
	@Column(name = "tradeDate")
	private Timestamp tradeDate​;
	
	@Column(name = "security")
	private String security​;
	
	@Column(name = "status")
	private String status​;
	
	@Column(name = "trader")
	private String trader​;
	
	@Column(name = "book")
	private String book​;
	
	@Column(name = "creationName")
	private String creationName​;
	
	@Column(name = "creationDate")
	private Timestamp creationDate​;
	
	@Column(name = "revisionName")
	private String revisionName​;
	
	@Column(name = "revisionDate")
	private Timestamp revisionDate​;
	
	@Column(name = "dealName")
	private String dealName​;
	
	@Column(name = "dealType")
	private String dealType​;
	
	@Column(name = "sourceListId")
	private String sourceListId​;
	
	@Column(name = "side")
	private String side​;
	
	public Integer getTradeId() {
		return TradeId;
	}
	
	public void setTradeId(Integer tradeId) {
		this.TradeId = tradeId;
	}
	
	public String getAccount() {
		return account​;
	}
	
	public void setAccount(String account ) {
		this.account​ = account;
	}
	
	public String getType() {
		return type​;
	}
	
	public void setType(String type) {
		this.type​ = type;
	}
	
	public Double getBuyQuantity() {
		return buyQuantity​;
	}
	
	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity​ = buyQuantity ;
	}
	
	public Timestamp getCreationDate() {
		return creationDate​;
	}
	
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate​ = creationDate;
	}
	
	public Timestamp getRevisionDate() {
		return creationDate​;
	}
	
	public void setRevisionDate(Timestamp revisionDate) {
		this.revisionDate​ = revisionDate;
	}
}

package com.nnk.springboot.domain;

import org.springframework.beans.factory.annotation.Required;

import jakarta.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {
    public BidList(String accountTest, String typeTest, double v) {
    }

    public Integer getBidListId() {
        return null;
    }

    public double getBidQuantity() {
        return 0;
    }

    public void setBidQuantity(double v) {
    }
    // TODO: Map columns in data table BIDLIST with corresponding java fields
}

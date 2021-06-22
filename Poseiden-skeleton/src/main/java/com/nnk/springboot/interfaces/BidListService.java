package com.nnk.springboot.interfaces;


import com.nnk.springboot.exceptions.NumberFormatException;
import com.nnk.springboot.forms.AddBidListForm;
import com.nnk.springboot.model.BidList;


public interface BidListService {

    void validate(AddBidListForm addBidListForm) throws NumberFormatException;

    void updateBid(Integer id, BidList bidList) throws NumberFormatException;

    void deleteBid(Integer id);
}

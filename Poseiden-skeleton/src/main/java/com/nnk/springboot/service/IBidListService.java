package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.DataNotFoundException;

import java.util.List;

public interface IBidListService {

    /**
     * get all BidList
     * @return
     */
    List<BidList> findAll();

    /**
     * find BidList by id
     * @param id
     * @return
     */
    BidList findById(Integer id) throws DataNotFoundException;

    /**
     * save given BidList
     * @param bidList
     */
    void save(BidList bidList);



    /**
     * delete given bidList
     * @param bidList
     */
    void delete(BidList bidList);

}

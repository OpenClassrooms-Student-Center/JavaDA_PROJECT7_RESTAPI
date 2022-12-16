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
     * save given bidList
     * @param bidList
     */
    void save(BidList bidList);

    /**
     * update given bidList
     * @param bidList
     */
    void update(BidList bidList) throws DataNotFoundException;

    /**
     * delete given bidList
     * @param bidList
     */
    void delete(BidList bidList);

}

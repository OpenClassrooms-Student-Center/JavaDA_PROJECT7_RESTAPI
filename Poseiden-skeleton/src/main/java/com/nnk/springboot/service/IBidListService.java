package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.DataNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface IBidListService {

    /**
     * get all BidList
     * @return
     */
    List<BidList> findAll();

    /**
     * find BidList by id
     *
     * @param id
     * @return
     */
    Optional<BidList> findById(Integer id) throws DataNotFoundException;

    /**
     * save given bidList
     * @param bidList
     */
    void save(BidList bidList);

    /**
     * update given bidList
     *
     * @param bidList
     * @return
     */
    BidList update(BidList bidList) ;


    /**
     * delete given bidList
     * @param bidList
     */
    void delete(Integer bidList);



}

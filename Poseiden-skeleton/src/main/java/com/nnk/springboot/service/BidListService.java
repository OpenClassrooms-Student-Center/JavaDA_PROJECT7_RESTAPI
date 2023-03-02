package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;

import java.util.List;

/**
 * The interface Bid list service.
 */
public interface BidListService {

    /**
     * Find all BidList.
     *
     * @return the list of BidList
     */
    List<BidList> findAll();

    /**
     * Find BidList by id.
     *
     * @param id the id
     * @return the bid list
     */
    BidList findById(Integer id);

    /**
     * Create bid list.
     *
     * @param bidList the bid list
     * @return the bid list
     */
    BidList create(BidListDto bidList);

    /**
     * Update bid list.
     *
     * @param id         the id
     * @param bidListDto the bid list dto
     * @return the bid list
     */
    BidList update(Integer id, BidListDto bidListDto);

    /**
     * Delete BidList by id.
     *
     * @param id the id
     */
    void delete(Integer id);
}

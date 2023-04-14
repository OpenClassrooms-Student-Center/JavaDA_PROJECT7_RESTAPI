package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
    public class BidListService {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this. bidListRepository= bidListRepository;
    }

    public List<BidList> findAll(){
        return bidListRepository.findAll();
    }

    public BidList getBidListById(Integer i)throws Exception{
        Optional<BidList> opt= bidListRepository.findById(i);
        return opt.get();
    }
//CREATE NEW BIDLIST
    public BidList validateNewBidList(BidList bid) throws Exception{
        return bidListRepository.save(bid);
    }
//UPDATE BIDLIST
    public BidList updateBidList(Integer id, BidList updatedBidListEntity) throws Exception {

        Optional<BidList> opt = bidListRepository.findById(id);
        BidList formerBidList = opt.get();
        formerBidList.setAccount(updatedBidListEntity.getAccount());
        formerBidList.setBid_quantity(updatedBidListEntity.getBid_quantity());
        formerBidList.setType(updatedBidListEntity.getType());
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return bidListRepository.save(formerBidList);



        }


    //DELETE BIDLIST
    public void deleteBidList(Integer id) throws Exception{
        Optional<BidList> opt = bidListRepository.findById(id);
        BidList bidListToDelete = opt.get();
        bidListRepository.delete(bidListToDelete);
    }

}

package com.nnk.springboot.service;



import com.nnk.springboot.exceptions.NumberFormatException;
import com.nnk.springboot.forms.AddBidListForm;
import com.nnk.springboot.interfaces.BidListService;
import com.nnk.springboot.model.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;


    @Override
    public void validate(BidList bidList) {

        BidList bid = new BidList();
        bid.setAccount(bidList.getAccount());
        bid.setType(bidList.getType());
        bid.setBidQuantity(bidList.getBidQuantity());
        bidListRepository.save(bid);

    }

    @Override
    public void updateBid(Integer id, BidList bidList){

        bidList.setBidListId(id);
        bidListRepository.save(bidList);
    }

    @Override
    public void deleteBid(Integer id) {
        BidList bidList = bidListRepository.findBidListByBidListId(id);
        bidListRepository.delete(bidList);
    }
}

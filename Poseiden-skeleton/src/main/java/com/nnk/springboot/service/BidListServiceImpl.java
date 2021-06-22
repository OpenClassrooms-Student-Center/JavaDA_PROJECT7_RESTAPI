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


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (java.lang.NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public void validate(AddBidListForm addBidListForm) throws NumberFormatException {

        if (!isNumeric(addBidListForm.getBidQuantity())){
            throw new NumberFormatException("The quantity must be a number.");
        }

        double bidQuantity = Double.parseDouble(addBidListForm.getBidQuantity());

        BidList bid = new BidList();
        bid.setAccount(addBidListForm.getAccount());
        bid.setType(addBidListForm.getType());
        bid.setBidQuantity(bidQuantity);
        bidListRepository.save(bid);

    }

    @Override
    public void updateBid(Integer id, BidList bidList) throws NumberFormatException {

        String quantity = bidList.getBidQuantity() == null ? " " : bidList.getBidQuantity().toString();

        if (!isNumeric(quantity)){
            throw new NumberFormatException("The quantity must be a number.");
        }

        bidList.setBidListId(id);
        bidListRepository.save(bidList);
    }

    @Override
    public void deleteBid(Integer id) {
        BidList bidList = bidListRepository.findBidListByBidListId(id);
        bidListRepository.delete(bidList);
    }
}

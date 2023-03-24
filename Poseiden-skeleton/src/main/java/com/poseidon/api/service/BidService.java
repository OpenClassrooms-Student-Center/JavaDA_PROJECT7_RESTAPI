package com.poseidon.api.service;

import com.poseidon.api.model.Bid;
import com.poseidon.api.model.dto.BidDto;
import com.poseidon.api.repositories.BidRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BidService {

    @Autowired
    BidRepository bidRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<Bid> findAllBids() {
        return bidRepository.findAll();
    }

    public Bid findBidById(Long id) {
        Optional<Bid> bid = bidRepository.findBidById(id);
        if (id != null && bid.isPresent()) {
            return bid.get();
        }
        throw new IllegalArgumentException("Could not find bid with id : " + id);
    }

    public boolean createBid(Bid bid) {
        if (bid != null && !bidRepository.findBidById(bid.getId()).isPresent()) {
            bidRepository.save(bid);
            log.info("[SERVICE] Created new bid for account : " + bid.getAccount() + " quantity : " + bid.getBidQuantity());
            return true;
        }
        throw new IllegalArgumentException("There was an error while creating the Bid");
    }

    public boolean updateBid(Long id, Bid bidEntityUpdated) {
        Optional<Bid> bidList = bidRepository.findBidById(id);
        if (id != null && bidList.isPresent()) {

            bidEntityUpdated.setId(id);
            bidRepository.save(bidEntityUpdated);

            log.info("[SERVICE] Updated account " + bidEntityUpdated.getAccount() + " with id " + id);
            return true;
        }
        throw new IllegalArgumentException("Could not find bid with id : " + id);
    }

    public boolean deleteBid(Long id) {
        Optional<Bid> bid = bidRepository.findBidById(id);
        if (id != null && bid.isPresent()) {
            bidRepository.delete(bid.get());
            log.info("[SERVICE] Deleted bid with id " + id);
            return true;
        }
        throw new IllegalArgumentException("Could not find bid with id : " + id);
    }

    public Bid convertDtoToEntity(BidDto bidDto) {
        return modelMapper.map(bidDto, Bid.class);
    }

    public BidDto convertEntityToDto(Bid bidEntity) { return modelMapper.map(bidEntity, BidDto.class); }
}

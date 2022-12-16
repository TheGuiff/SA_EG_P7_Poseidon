package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.BidList;
import com.nnk.springboot.dal.entity.Rating;
import com.nnk.springboot.dal.repositories.BidListRepository;
import com.nnk.springboot.dal.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    public Optional<BidList> getBidList (final Integer id) {
        return bidListRepository.findById(id);
    }

    public Iterable<BidList> getBidLists() {
        return bidListRepository.findAll();
    }

    public  void deleteBidList(final Integer id) {
        bidListRepository.deleteById(id);
    }

    public BidList saveBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }
}

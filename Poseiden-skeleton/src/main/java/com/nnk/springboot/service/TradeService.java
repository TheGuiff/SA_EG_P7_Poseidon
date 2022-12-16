package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.Rating;
import com.nnk.springboot.dal.entity.Trade;
import com.nnk.springboot.dal.repositories.RatingRepository;
import com.nnk.springboot.dal.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public Optional<Trade> getTrade (final Integer id) {
        return tradeRepository.findById(id);
    }

    public Iterable<Trade> getTrades() {
        return tradeRepository.findAll();
    }

    public  void deleteTrade(final Integer id) {
        tradeRepository.deleteById(id);
    }

    public Trade saveTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

}

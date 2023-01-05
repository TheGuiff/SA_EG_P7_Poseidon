package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.Trade;
import com.nnk.springboot.dal.repositories.TradeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TradeServiceTest {

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    TradeService tradeService;

    private final List<Trade> trades = new ArrayList<>();
    private Trade trade;

    @Test
    public void tradeTest() {
        tradeRepository.deleteAll();

        Trade trade1 = new Trade("account1","type1",20d);
        tradeRepository.save(trade1);
        Trade trade2 = new Trade("account2","type2",200d);
        tradeRepository.save(trade2);
        Trade trade3 = new Trade("account3","type3",250d);

        //get all
        Iterable<Trade> tradeIterable = tradeService.getTrades();
        tradeIterable.forEach(trades::add);
        assertEquals(2,trades.size());

        //get by id
        trade = trades.get(0);
        Integer id = trade.getTradeId();
        Optional<Trade> tradeOptional = tradeService.getTrade(id);
        assertTrue(tradeOptional.isPresent());

        //save
        trade = tradeService.saveTrade(trade3);
        Assert.assertNotNull(trade.getTradeId());
        assertEquals(250d, trade.getBuyQuantity());

        //delete
        tradeService.deleteTrade(id);
        tradeOptional = tradeRepository.findById(id);
        Assert.assertFalse(tradeOptional.isPresent());

        tradeRepository.deleteAll();
    }

}

package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.BidList;
import com.nnk.springboot.dal.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
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
public class BidListServiceTest {

    @Autowired
    BidListRepository bidListRepository;

    @Autowired
    BidListService bidListService;

    private final List<BidList> bidLists = new ArrayList<>();
    private BidList bidList;

    @Test
    public void bidListTest() {
        bidListRepository.deleteAll();

        BidList bidList1 = new BidList("account1","type1",20d);
        bidListRepository.save(bidList1);
        BidList bidList2 = new BidList("account2","type2",200d);
        bidListRepository.save(bidList2);
        BidList bidList3 = new BidList("account3","type3",250d);

        //get all
        Iterable<BidList> bidListIterable = bidListService.getBidLists();
        bidListIterable.forEach(bidLists::add);
        assertEquals(2,bidLists.size());

        //get by id
        bidList = bidLists.get(0);
        Integer id = bidList.getBidListId();
        Optional<BidList> bidListOptional = bidListService.getBidList(id);
        assertTrue(bidListOptional.isPresent());

        //save
        bidList = bidListService.saveBidList(bidList3);
        Assert.assertNotNull(bidList.getBidListId());
        assertEquals(250d, bidList.getBidQuantity());

        //delete
        bidListService.deleteBidList(id);
        bidListOptional = bidListRepository.findById(id);
        Assert.assertFalse(bidListOptional.isPresent());

        bidListRepository.deleteAll();
    }

}

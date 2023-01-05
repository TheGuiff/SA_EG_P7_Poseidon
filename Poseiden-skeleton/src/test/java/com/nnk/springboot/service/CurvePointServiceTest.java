package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.BidList;
import com.nnk.springboot.dal.entity.CurvePoint;
import com.nnk.springboot.dal.repositories.BidListRepository;
import com.nnk.springboot.dal.repositories.CurvePointRepository;
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
public class CurvePointServiceTest {

    @Autowired
    CurvePointRepository curvePointRepository;

    @Autowired
    CurvePointService curvePointService;

    private final List<CurvePoint> curvePoints = new ArrayList<>();
    private CurvePoint curvePoint;

    @Test
    public void bidListTest() {
        curvePointRepository.deleteAll();

        CurvePoint curvePoint1 = new CurvePoint(10,20d,30d);
        curvePointRepository.save(curvePoint1);
        CurvePoint curvePoint2 = new CurvePoint(11,20d,30d);
        curvePointRepository.save(curvePoint2);
        CurvePoint curvePoint3 = new CurvePoint(12,20d,40d);

        //get all
        Iterable<CurvePoint> curvePointIterable = curvePointService.getCurvedPoints();
        curvePointIterable.forEach(curvePoints::add);
        assertEquals(2,curvePoints.size());

        //get by id
        curvePoint = curvePoints.get(0);
        Integer id = curvePoint.getId();
        Optional<CurvePoint> curvePointOptional = curvePointService.getCurvePoint(id);
        assertTrue(curvePointOptional.isPresent());

        //save
        curvePoint = curvePointService.saveCurvePoint(curvePoint3);
        Assert.assertNotNull(curvePoint.getId());
        assertEquals(40d, curvePoint.getValue());

        //delete
        curvePointService.deleteCurvePoint(id);
        curvePointOptional = curvePointRepository.findById(id);
        Assert.assertFalse(curvePointOptional.isPresent());

        curvePointRepository.deleteAll();
    }

}

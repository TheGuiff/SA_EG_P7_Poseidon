package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.CurvePoint;
import com.nnk.springboot.dal.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    public Optional<CurvePoint> getCurvePoint (final Integer id) {
        return curvePointRepository.findById(id);
    }

    public Iterable<CurvePoint> getCurvedPoints() {
        return curvePointRepository.findAll();
    }

    public  void deleteCurvePoint(final Integer id) {
        curvePointRepository.deleteById(id);
    }

    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

}

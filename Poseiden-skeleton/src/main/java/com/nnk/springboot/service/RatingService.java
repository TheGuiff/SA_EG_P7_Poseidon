package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.Rating;
import com.nnk.springboot.dal.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Optional<Rating> getRating (final Integer id) {
        return ratingRepository.findById(id);
    }

    public Iterable<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    public  void deleteRating(final Integer id) {
        ratingRepository.deleteById(id);
    }

    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

}

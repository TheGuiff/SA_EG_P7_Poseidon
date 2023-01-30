package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.Rating;
import com.nnk.springboot.dal.repositories.RatingRepository;
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
public class RatingServiceTest {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    RatingService ratingService;

    private final List<Rating> ratings = new ArrayList<>();
    private Rating rating;

    @Test
    public void ratingTest() {
        ratingRepository.deleteAll();

        Rating rating1 = new Rating("A","A","A",5);
        ratingRepository.save(rating1);
        Rating rating2 = new Rating("A","A","A",6);
        ratingRepository.save(rating2);
        Rating rating3 = new Rating("A","A","A",7);

        //get all
        Iterable<Rating> ratingIterable = ratingService.getRatings();
        ratingIterable.forEach(ratings::add);
        assertEquals(2,ratings.size());

        //get by id
        rating = ratings.get(0);
        Integer id = rating.getId();
        Optional<Rating> ratingOptional = ratingService.getRating(id);
        assertTrue(ratingOptional.isPresent());

        //save
        rating = ratingService.saveRating(rating3);
        Assert.assertNotNull(rating.getId());
        assertEquals(7, rating.getOrderNumber());

        //delete
        ratingService.deleteRating(id);
        ratingOptional = ratingRepository.findById(id);
        Assert.assertFalse(ratingOptional.isPresent());

        ratingRepository.deleteAll();
    }
}

package com.nnk.springboot.dal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "moodys_rating")
    private String moodysRating;

    @Column(name = "sand_p_rating")
    private String sandPRating;

    @Column(name = "fitch_rating")
    private String fitchRating;

    @Column(name = "order_number")
    private Integer orderNumber;

    public Rating (String moodysRatingIn,
                   String sandPRatingIn,
                   String fitchRatingIn,
                   Integer orderNumberIn) {
        this.setMoodysRating(moodysRatingIn);
        this.setSandPRating(sandPRatingIn);
        this.setFitchRating(fitchRatingIn);
        this.setOrderNumber(orderNumberIn);
    }

    public Rating() {

    }
}

package com.nnk.springboot.dal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "moodys_rating")
    @NotBlank(message = "Moody's rating id is mandatory")
    private String moodysRating;

    @Column(name = "sand_p_rating")
    @NotBlank(message = "Sand rating id is mandatory")
    private String sandPRating;

    @Column(name = "fitch_rating")
    @NotBlank(message = "Fitch rating id is mandatory")
    private String fitchRating;

    @Column(name = "order_number")
    @NotNull(message = "Order number is mandatory")
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

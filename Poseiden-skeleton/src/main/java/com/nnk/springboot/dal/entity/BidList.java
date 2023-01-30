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
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer BidListId;

    @Column
    @NotBlank(message = "Account is mandatory")
    private String account;

    @Column
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Column(name = "bid_quantity")
    @NotNull(message = "Bid quantity is mandatory")
    Double bidQuantity;

    @Column(name = "ask_quantity")
    Double askQuantity;

    @Column
    Double bid;

    @Column
    Double ask;

    @Column
    private String benchmark;

    @Column(name = "bid_list_date")
    Timestamp bidListDate;

    @Column
    private String commentary;

    @Column
    private String security;

    @Column
    private String status;

    @Column
    private String trader;

    @Column
    private String book;

    @Column(name="creation_name")
    private String creationName;

    @Column(name = "creation_date")
    Timestamp creationDate;

    @Column(name="revision_name")
    private String revisionName;

    @Column(name = "revision_date")
    Timestamp revisionDate;

    @Column(name="deal_name")
    private String dealName;

    @Column(name="deal_type")
    private String dealType;

    @Column(name="source_list_id")
    private String sourceListId;

    @Column
    private String side;

    public BidList (String accountIn,
                    String typeIn,
                    Double bidQuantityIn) {
        this.setAccount(accountIn);
        this.setType(typeIn);
        this.setBidQuantity(bidQuantityIn);
    }

    public BidList() {

    }
}

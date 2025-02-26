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
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tradeId;

    @Column
    @NotBlank(message = "Account is mandatory")
    private String account;

    @Column
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Column(name = "buy_quantity")
    @NotNull(message = "Buy quantity is mandatory")
    private Double buyQuantity;

    @Column(name = "sell_quantity")
    private Double sellQuantity;

    @Column(name = "buy_price")
    private Double buyPrice;

    @Column(name = "sell_price")
    private Double sellPrice;

    @Column
    private String benchmark;

    @Column(name = "trade_date")
    private Timestamp tradeDate;

    @Column
    private String security;

    @Column
    private String status;

    @Column
    private String trader;

    @Column
    private String book;

    @Column (name = "creation_name")
    private String creationName;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column (name = "revision_name")
    private String revisionName;

    @Column(name = "revision_date")
    private Timestamp revisionDate;

    @Column (name = "deal_name")
    private String dealName;

    @Column (name = "deal_type")
    private String dealType;

    @Column (name = "source_list_id")
    private String sourceListId;

    @Column
    private String side;

    public Trade (String accountIn,
                  String typeIn,
                  Double buyQuantityIn) {
        this.setAccount(accountIn);
        this.setType(typeIn);
        this.setBuyQuantity(buyQuantityIn);
    }

    public Trade() {

    }
}

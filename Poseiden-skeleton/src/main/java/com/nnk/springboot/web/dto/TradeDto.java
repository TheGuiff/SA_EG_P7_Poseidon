package com.nnk.springboot.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class TradeDto {

    private String account;
    private String type;

}

package com.nnk.springboot.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "trade_id")
    private Integer tradeId;

    @Column(length = 30)
    @Length(max = 30)
    private String account;

    @Column(length = 30)
    @Length(max = 30)
    private String type;

    // @Column(name = "buy_quantity")
    private Double buyQuantity;

    // @Column(name = "sell_quantity")
    private Double sellQuantity;

    // @Column(name = "buy_price")
    private Double buyPrice;

    // @Column(name = "sell_price")
    private Double sellPrice;

    // @Column(name = "trade_date")
    private LocalDateTime tradeDate;

    @Column(length = 125)
    @Length(max = 125)
    private String security;

    @Column(length = 10)
    @Length(max = 10)
    private String status;

    @Column(length = 125)
    @Length(max = 125)
    private String trader;

    @Column(length = 125)
    @Length(max = 125)
    private String benchmark;

    @Column(length = 125)
    @Length(max = 125)
    private String book;

    @Column(length = 125)
    @Length(max = 125)
    private String creationName;

    // @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(length = 125)
    @Length(max = 125)
    private String revisionName;

    // @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    @Column(length = 125)
    @Length(max = 125)
    private String dealName;

    @Column(length = 125)
    @Length(max = 125)
    private String dealType;

    @Column(length = 125)
    @Length(max = 125)
    private String sourceListId;

//    @Column(length = 125)
//    @Length(max = 125)
    private String side;

}

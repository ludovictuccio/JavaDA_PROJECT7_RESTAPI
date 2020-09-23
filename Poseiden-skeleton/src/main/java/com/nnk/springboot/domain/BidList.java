package com.nnk.springboot.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

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
@Table(name = "bid_list")
public class BidList {

    /**
     * For value=12, the number must be < 1 000 000 000 000
     */
    private static final int BID_QUANTITY_INT_MAX_SIZE = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "bid_list_id")
    private Integer bidListId;

    @NotBlank
    @Column(length = 30)
    @Length(max = 30)
    private String account;

    @NotBlank
    @Column(length = 30)
    @Length(max = 30)
    private String type;

    @NotNull
    @PositiveOrZero
    @Digits(fraction = 2, integer = BID_QUANTITY_INT_MAX_SIZE, message = "Must be a number < 1 000 000 000 000  with 2 fractional digits max")
    private Double bidQuantity;

    // @Column(name = "ask_quantity")
    private Double askQuantity;

    // @Column(name = "bid")
    private Double bid;

    // @Column(name = "ask")
    private Double ask;

    @Column(length = 125)
    @Length(max = 125)
    private String benchmark;

    // @Column(name = "bid_list_date")
    private LocalDateTime bidListDate;

    @Column(length = 125)
    @Length(max = 125)
    private String commentary;

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

    @Column(length = 125)
    @Length(max = 125)
    private String side;

}

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
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
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
    @Column(name = "bid_list_id")
    private Integer bidListId;

    @NotBlank
    @Column(length = 30)
    @Size(min = 2, max = 30)
    private String account;

    @NotBlank
    @Column(length = 30)
    @Size(min = 2, max = 30)
    private String type;

    @NotNull
    @PositiveOrZero
    @Digits(fraction = 2, integer = BID_QUANTITY_INT_MAX_SIZE, message = "Must be a number < 1 000 000 000 000  with 2 fractional digits max")
    private Double bidQuantity;

    @Column(name = "ask_quantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Column(length = 125)
    @Size(max = 125)
    private String benchmark;

    @JsonFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @DateTimeFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @Column(name = "bid_list_date")
    private LocalDateTime bidListDate;// a date before actual date

    @Column(length = 125)
    @Size(max = 125)
    private String commentary;

    @Column(length = 125)
    @Size(max = 125)
    private String security;

    @Column(length = 10)
    @Size(max = 10)
    private String status;

    @Column(length = 125)
    @Size(max = 125)
    private String trader;

    @Column(length = 125)
    @Size(max = 125)
    private String book;

    @Column(length = 125)
    @Size(max = 125)
    private String creationName;// for create only

    @JsonFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @DateTimeFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @Column(name = "creation_date")
    private LocalDateTime creationDate;// generate while create success

    @Column(length = 125)
    @Size(max = 125)
    private String revisionName; // for update only

    @JsonFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @DateTimeFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @Column(name = "revision_date")
    private LocalDateTime revisionDate; // generate while update success

    @Column(length = 125)
    @Size(max = 125)
    private String dealName;

    @Column(length = 125)
    @Size(max = 125)
    private String dealType;

    @Column(length = 125)
    @Size(max = 125)
    private String sourceListId;

    @Column(length = 125)
    @Size(max = 125)
    private String side;

    public BidList(Integer bidId,
            @NotBlank @Size(min = 2, max = 30) String account,
            @NotBlank @Size(min = 2, max = 30) String type,
            @NotNull @PositiveOrZero @Digits(fraction = 2, integer = 12, message = "Must be a number < 1 000 000 000 000  with 2 fractional digits max") Double bidQuantity,
            Double askQuantity, Double bid, Double ask,
            @Size(max = 125) String benchmark, LocalDateTime bidListDate,
            @Size(max = 125) String commentary,
            @Size(max = 125) String security, @Size(max = 10) String status,
            @Size(max = 125) String trader, @Size(max = 125) String book,
            @Size(max = 125) String creationName,
            @Size(max = 125) String revisionName,
            @Size(max = 125) String dealName, @Size(max = 125) String dealType,
            @Size(max = 125) String sourceListId,
            @Size(max = 125) String side) {
        super();
        this.bidListId = bidId;
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
        this.askQuantity = askQuantity;
        this.bid = bid;
        this.ask = ask;
        this.benchmark = benchmark;
        this.bidListDate = bidListDate;
        this.commentary = commentary;
        this.security = security;
        this.status = status;
        this.trader = trader;
        this.book = book;
        this.creationName = creationName;
        this.revisionName = revisionName;
        this.dealName = dealName;
        this.dealType = dealType;
        this.sourceListId = sourceListId;
        this.side = side;
    }

}

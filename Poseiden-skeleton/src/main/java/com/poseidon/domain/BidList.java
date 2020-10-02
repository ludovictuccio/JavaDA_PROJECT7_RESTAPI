package com.poseidon.domain;

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
import com.poseidon.util.Constants;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_list_id")
    private Integer bidListId;

    @NotBlank
    @Size(min = Constants.SIZE_2, max = Constants.SIZE_30)
    private String account;

    @NotBlank
    @Size(min = Constants.SIZE_2, max = Constants.SIZE_30)
    private String type;

    /**
     * For value = 12, the number must be < 1 000 000 000 000.
     */
    @NotNull
    @PositiveOrZero
    @Digits(fraction = Constants.SIZE_2, integer = Constants.SIZE_12, message = "Must be a number < 1 000 000 000 000  with 2 fractional digits max")
    private Double bidQuantity;

    @Column(name = "ask_quantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Size(max = Constants.SIZE_125)
    private String benchmark;

    @JsonFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @DateTimeFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @Column(name = "bid_list_date")
    private LocalDateTime bidListDate; // a date before actual date

    @Size(max = Constants.SIZE_125)
    private String commentary;

    @Size(max = Constants.SIZE_125)
    private String security;

    @Size(max = Constants.SIZE_10)
    private String status;

    @Size(max = Constants.SIZE_125)
    private String trader;

    @Size(max = Constants.SIZE_125)
    private String book;

    @Size(max = Constants.SIZE_125)
    private String creationName; // for create only

    @JsonFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @DateTimeFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @Column(name = "creation_date")
    private LocalDateTime creationDate; // generate while create success

    @Size(max = Constants.SIZE_125)
    private String revisionName; // for update only

    @JsonFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @DateTimeFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @Column(name = "revision_date")
    private LocalDateTime revisionDate; // generate while update success

    @Size(max = Constants.SIZE_125)
    private String dealName;

    @Size(max = Constants.SIZE_125)
    private String dealType;

    @Size(max = Constants.SIZE_125)
    private String sourceListId;

    @Size(max = Constants.SIZE_125)
    private String side;

    public BidList(Integer id,
            @NotBlank @Size(min = Constants.SIZE_2, max = Constants.SIZE_30) final String accountBid,
            @NotBlank @Size(min = Constants.SIZE_2, max = Constants.SIZE_30) final String typeBid,
            @NotNull @PositiveOrZero @Digits(fraction = Constants.SIZE_2, integer = Constants.SIZE_12, message = "Must be a number < 1 000 000 000 000  with 2 fractional digits max") final Double bidQuantityBid,
            final Double askQuantityBid, final Double bidDouble,
            final Double askBid,
            @Size(max = Constants.SIZE_125) final String benchmarkBid,
            final LocalDateTime bidListDateBid,
            @Size(max = Constants.SIZE_125) final String commentaryBid,
            @Size(max = Constants.SIZE_125) final String securityBid,
            @Size(max = Constants.SIZE_10) final String statusBid,
            @Size(max = Constants.SIZE_125) final String traderBid,
            @Size(max = Constants.SIZE_125) final String bookBid,
            @Size(max = Constants.SIZE_125) final String creationNameBid,
            @Size(max = Constants.SIZE_125) final String revisionNameBid,
            @Size(max = Constants.SIZE_125) final String dealNameBid,
            @Size(max = Constants.SIZE_125) final String dealTypeBid,
            @Size(max = Constants.SIZE_125) final String sourceListIdBid,
            @Size(max = Constants.SIZE_125) final String sideBid) {
        super();
        this.bidListId = id;
        this.account = accountBid;
        this.type = typeBid;
        this.bidQuantity = bidQuantityBid;
        this.askQuantity = askQuantityBid;
        this.bid = bidDouble;
        this.ask = askBid;
        this.benchmark = benchmarkBid;
        this.bidListDate = bidListDateBid;
        this.commentary = commentaryBid;
        this.security = securityBid;
        this.status = statusBid;
        this.trader = traderBid;
        this.book = bookBid;
        this.creationName = creationNameBid;
        this.revisionName = revisionNameBid;
        this.dealName = dealNameBid;
        this.dealType = dealTypeBid;
        this.sourceListId = sourceListIdBid;
        this.side = sideBid;
    }

}

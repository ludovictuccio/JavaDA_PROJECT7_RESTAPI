package com.poseidon.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poseidon.util.Constants;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @NotNull(message = "Account can't be empty.")
    @Column(name = "account")
    @Size(min = Constants.SIZE_1, max = Constants.SIZE_30, message = "Size must be between 1 & 30.")
    private String account;

    @NotNull(message = "Type can't be empty.")
    @Column(name = "type")
    @Size(min = Constants.SIZE_1, max = Constants.SIZE_30, message = "Size must be between 1 & 30.")
    private String type;

    @Column(name = "buy_quantity")
    private Double buyQuantity;

    @Column(name = "sell_quantity")
    private Double sellQuantity;

    @Column(name = "buy_price")
    private Double buyPrice;

    @Column(name = "sell_price")
    private Double sellPrice;

    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @DateTimeFormat(pattern = Constants.DATE_PATTERN)
    @Column(name = "trade_date")
    private LocalDateTime tradeDate; // a date before actual date

    @Column(name = "security")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    private String security;

    @Column(name = "status")
    @Size(max = Constants.SIZE_10, message = "Status size must be equal or less than 10")
    private String status;

    @Column(name = "trader")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    private String trader;

    @Column(name = "benchmark")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    private String benchmark;

    @Column(name = "book")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    private String book;

    @NotBlank(message = "The creation name can't be empty.")
    @Column(name = "creation_name")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    private String creationName;

    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @DateTimeFormat(pattern = Constants.DATE_PATTERN)
    @Column(name = "creation_date")
    private LocalDateTime creationDate; // for add new trade

    @Column(name = "revision_name")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    private String revisionName; // for update

    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @DateTimeFormat(pattern = Constants.DATE_PATTERN)
    @Column(name = "revision_date")
    private LocalDateTime revisionDate; // for update

    @Column(name = "deal_name")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    private String dealName;

    @Column(name = "deal_type")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    private String dealType;

    @Column(name = "source_list_id")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    private String sourceListId;

    @Column(name = "side")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    private String side;

    public Trade(
            @NotNull @Size(min = Constants.SIZE_1, max = Constants.SIZE_30) final String accountTrade,
            @NotNull @Size(min = Constants.SIZE_1, max = Constants.SIZE_30) final String typeTrade,
            final Double buyQuantityTrade, final Double sellQuantityTrade,
            final Double buyPriceTrade, final Double sellPriceTrade,
            final LocalDateTime date,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String securityTrade,
            @Size(max = Constants.SIZE_10, message = "Status size must be equal or less than 10") final String statusTrade,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String traderTrade,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String benchmarkTrade,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String bookTrade,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String creationNameTrade,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String revisionNameTrade,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String dealNameTrade,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String dealTypeTrade,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String sourceListIdTrade,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String sideTrade) {
        super();
        this.account = accountTrade;
        this.type = typeTrade;
        this.buyQuantity = buyQuantityTrade;
        this.sellQuantity = sellQuantityTrade;
        this.buyPrice = buyPriceTrade;
        this.sellPrice = sellPriceTrade;
        this.tradeDate = date;
        this.security = securityTrade;
        this.status = statusTrade;
        this.trader = traderTrade;
        this.benchmark = benchmarkTrade;
        this.book = bookTrade;
        this.creationName = creationNameTrade;
        this.revisionName = revisionNameTrade;
        this.dealName = dealNameTrade;
        this.dealType = dealTypeTrade;
        this.sourceListId = sourceListIdTrade;
        this.side = sideTrade;
    }

}

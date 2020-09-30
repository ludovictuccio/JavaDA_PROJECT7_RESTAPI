package com.nnk.springboot.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {

    @Min(value = -128) // tynint(4)
    @Max(value = 127)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @NotNull
    @Column(name = "account")
    @Size(min = 1, max = 30)
    private String account;

    @NotNull
    @Column(name = "type")
    @Size(min = 1, max = 30)
    private String type;

    @Column(name = "buy_quantity")
    private Double buyQuantity;

    @Column(name = "sell_quantity")
    private Double sellQuantity;

    @Column(name = "buy_price")
    private Double buyPrice;

    @Column(name = "sell_price")
    private Double sellPrice;

    @DateTimeFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @Column(name = "trade_date")
    private LocalDateTime tradeDate; // a date before actual date

    @Column(name = "security")
    @Size(max = 125)
    private String security;

    @Column(name = "status")
    @Size(max = 10)
    private String status;

    @Column(name = "trader")
    @Size(max = 125)
    private String trader;

    @Column(name = "benchmark")
    @Size(max = 125)
    private String benchmark;

    @Column(name = "book")
    @Size(max = 125)
    private String book;

    @Column(name = "creation_name")
    @Size(max = 125)
    private String creationName;

    @DateTimeFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @Column(name = "creation_date")
    private LocalDateTime creationDate; // for add new trade

    @Column(name = "revision_name")
    @Size(max = 125)
    private String revisionName; // for update

    @DateTimeFormat(pattern = "dd/MM/yyyy' 'HH:mm")
    @Column(name = "revision_date")
    private LocalDateTime revisionDate; // for update

    @Column(name = "deal_name")
    @Size(max = 125)
    private String dealName;

    @Column(name = "deal_type")
    @Size(max = 125)
    private String dealType;

    @Column(name = "source_list_id")
    @Size(max = 125)
    private String sourceListId;

    @Column(name = "side")
    @Size(max = 125)
    private String side;

    public Trade(@NotNull @Size(max = 30) final String account,
            @NotNull @Size(max = 30) final String type,
            final Double buyQuantity, final Double sellQuantity,
            final Double buyPrice, final Double sellPrice,
            final LocalDateTime tradeDate,
            @Size(max = 125) final String security,
            @Size(max = 10) final String status,
            @Size(max = 125) final String trader,
            @Size(max = 125) final String benchmark,
            @Size(max = 125) final String book,
            @Size(max = 125) final String creationName,
            @Size(max = 125) final String revisionName,
            @Size(max = 125) final String dealName,
            @Size(max = 125) final String dealType,
            @Size(max = 125) final String sourceListId,
            @Size(max = 125) final String side) {
        super();
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
        this.sellQuantity = sellQuantity;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.tradeDate = tradeDate;
        this.security = security;
        this.status = status;
        this.trader = trader;
        this.benchmark = benchmark;
        this.book = book;
        this.creationName = creationName;
        this.revisionName = revisionName;
        this.dealName = dealName;
        this.dealType = dealType;
        this.sourceListId = sourceListId;
        this.side = side;
    }

}

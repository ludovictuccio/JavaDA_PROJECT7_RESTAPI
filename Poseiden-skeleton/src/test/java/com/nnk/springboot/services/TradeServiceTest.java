package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class TradeServiceTest {

    @Autowired
    public ITradeService tradeService;

    @MockBean
    private TradeRepository tradeRepository;

    private Trade trade;
    private Trade tradeTwo;
    private Trade result;

    private List<Trade> allTrades;

    @BeforeEach
    public void setUpPerTest() {
        allTrades = new ArrayList<>();

        trade = new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.of(1999, 11, 11, 11, 01), "security", "status",
                "trader", "benchmark", "book", "creationName", "", "dealName",
                "dealType", "sourceListId", "side");
        trade.setTradeId(1);
        allTrades.add(trade);

        tradeTwo = new Trade("account 2", "type 2", 20d, 20d, 20d, 20d,
                LocalDateTime.of(2000, 01, 01, 12, 12), "security", "status",
                "trader 2", "benchmark 2", "book 2", "creationName 2", "",
                "dealName 2", "dealType 2", "sourceListId 2", "side 2");
        tradeTwo.setTradeId(2);
        allTrades.add(tradeTwo);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - OK")
    public void givenValidRuleName_whenSave_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = tradeService.saveTrade(trade);

        // THEN
        assertThat(result.getTradeId()).isEqualTo(1);
        assertThat(result.getCreationDate()).isNotNull();
        assertThat(result.getRevisionDate()).isNull();
        assertThat(result.getAccount()).isEqualTo("account");
        assertThat(result.getType()).isEqualTo("type");
        assertThat(result.getBuyQuantity()).isEqualTo(10d);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - ERROR - Trade date after actual date")
    public void givenTradeDateAfterActualDate_whenSave_thenReturnNull() {
        // GIVEN
        trade.setTradeDate(LocalDateTime.now().plusHours(1));

        // WHEN
        result = tradeService.saveTrade(trade);

        // THEN
        assertThat(result).isNull();
        verify(tradeRepository, times(0)).save(trade);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - ERROR - Attribute size > max allowed")
    public void givenInvalidRuleName_whenSave_thenReturnNull() {
        // GIVEN
        trade.setStatus("123456789 12");

        // WHEN
        result = tradeService.saveTrade(trade);

        // THEN
        assertThat(result).isNull();
        verify(tradeRepository, times(0)).save(trade);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - ERROR - Empty account")
    public void givenEmptyAccountEntry_whenSave_thenReturnNull() {
        // GIVEN
        trade.setAccount("");

        // WHEN
        result = tradeService.saveTrade(trade);

        // THEN
        assertThat(result).isNull();
        verify(tradeRepository, times(0)).save(trade);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - ERROR - Null account")
    public void givenNullAccount_whenSave_thenReturnNull() {
        // GIVEN
        trade.setAccount(null);

        // WHEN
        result = tradeService.saveTrade(trade);

        // THEN
        assertThat(result).isNull();
        verify(tradeRepository, times(0)).save(trade);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - ERROR - Empty type")
    public void givenEmptyType_whenSave_thenReturnNull() {
        // GIVEN
        trade.setType("");

        // WHEN
        result = tradeService.saveTrade(trade);

        // THEN
        assertThat(result).isNull();
        verify(tradeRepository, times(0)).save(trade);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - ERROR - Null type")
    public void givenNullType_whenSave_thenReturnNull() {
        // GIVEN
        trade.setType(null);

        // WHEN
        result = tradeService.saveTrade(trade);

        // THEN
        assertThat(result).isNull();
        verify(tradeRepository, times(0)).save(trade);
    }
}

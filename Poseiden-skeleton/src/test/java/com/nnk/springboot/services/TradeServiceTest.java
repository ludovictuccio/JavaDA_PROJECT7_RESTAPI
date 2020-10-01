package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        trade.setCreationName("Creation name");

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
        trade.setCreationName("Creation name");

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
        trade.setCreationName("Creation name");

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
        trade.setCreationName("Creation name");

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
        trade.setCreationName("Creation name");

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
        trade.setCreationName("Creation name");

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
        trade.setCreationName("Creation name");

        // WHEN
        result = tradeService.saveTrade(trade);

        // THEN
        assertThat(result).isNull();
        verify(tradeRepository, times(0)).save(trade);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - ERROR - Trade date after actual date")
    public void givenInvalidTradeDate_whenSave_thenReturnNull() {
        // GIVEN
        trade.setTradeDate(LocalDateTime.now().plusMonths(1));
        trade.setCreationName("Creation name");

        // WHEN
        result = tradeService.saveTrade(trade);

        // THEN
        assertThat(result).isNull();
        verify(tradeRepository, times(0)).save(trade);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - ERROR - empty creation name")
    public void givenEmptyCreationName_whenSave_thenReturnNull() {
        // GIVEN
        trade.setCreationName("");

        // WHEN
        result = tradeService.saveTrade(trade);

        // THEN
        assertThat(result).isNull();
        verify(tradeRepository, times(0)).save(trade);
    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - OK - 2 trades")
    public void givenTwoTrades_whenFindAll_thenReturnTwoSizeList() {
        // GIVEN
        when(tradeService.findAllTrade()).thenReturn(allTrades);

        // WHEN
        List<Trade> resultList = tradeService.findAllTrade();

        // THEN
        assertThat(resultList.size()).isNotNull();
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(tradeRepository.findAll().size()).isEqualTo(2);
        assertThat(tradeRepository.findAll().get(0).getTradeId()).isNotNull();
        assertThat(tradeRepository.findAll().get(0).getAccount())
                .isEqualTo("account");
        assertThat(tradeRepository.findAll().get(1).getTradeId()).isNotNull();
        assertThat(tradeRepository.findAll().get(1).getAccount())
                .isEqualTo("account 2");
    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - Ok - Empty list / size = 0")
    public void givenZeroTrade_whenFindAll_thenReturnEmptyList() {
        // GIVEN
        allTrades.clear();
        when(tradeService.findAllTrade()).thenReturn(allTrades);

        // WHEN
        List<Trade> resultList = tradeService.findAllTrade();

        // THEN
        assertThat(resultList.size()).isNotNull();
        assertThat(resultList.size()).isEqualTo(0);
        assertThat(tradeRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - OK - Existing id")
    public void givenValidId_whenUpdate_thenReturnTrue() {
        // GIVEN
        when(tradeRepository.save(trade)).thenReturn(trade);
        when(tradeRepository.save(tradeTwo)).thenReturn(tradeTwo);
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeService.findAllTrade()).thenReturn(allTrades);

        Trade tradeForUpdate = new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.now().minusMonths(2), "security", "status",
                "trader", "benchmark", "book updated", "creationName",
                "REVISION NAME", "dealName", "dealType", "sourceListId",
                "side");

        // WHEN
        boolean result = tradeService.updateTrade(1, tradeForUpdate);

        // THEN
        assertThat(result).isTrue();
        verify(tradeRepository, times(1)).delete(trade);
        verify(tradeRepository, times(1)).save(tradeForUpdate);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Bad id")
    public void givenBadId_whenUpdate_thenReturnFalse() {
        // GIVEN
        when(tradeRepository.save(trade)).thenReturn(trade);
        when(tradeRepository.save(tradeTwo)).thenReturn(tradeTwo);
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeService.findAllTrade()).thenReturn(allTrades);

        Trade tradeForUpdate = new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.now().minusMonths(2), "security", "status",
                "trader", "benchmark", "book updated", "creationName",
                "REVISION NAME", "dealName", "dealType", "sourceListId",
                "side");

        // WHEN
        boolean result = tradeService.updateTrade(99, tradeForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(tradeRepository, times(0)).delete(trade);
        verify(tradeRepository, times(0)).save(tradeForUpdate);
        assertThat(tradeRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(tradeRepository.findAll().get(0).getTradeId()).isEqualTo(1);
        assertThat(tradeRepository.findAll().get(0).getRevisionDate()).isNull();
        assertThat(tradeRepository.findAll().get(0).getBook())
                .isEqualTo("book");
        assertThat(tradeRepository.findAll().get(1).getTradeId()).isEqualTo(2);
        assertThat(tradeRepository.findAll().get(1).getBook())
                .isEqualTo("book 2");
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Empty revision name")
    public void givenNoRevisionName_whenUpdate_thenReturnFalse() {
        // GIVEN
        when(tradeRepository.save(trade)).thenReturn(trade);
        when(tradeRepository.save(tradeTwo)).thenReturn(tradeTwo);
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeService.findAllTrade()).thenReturn(allTrades);

        Trade tradeForUpdate = new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.now().minusMonths(2), "security", "status",
                "trader", "benchmark", "book updated", "creationName", "",
                "dealName", "dealType", "sourceListId", "side");

        // WHEN
        boolean result = tradeService.updateTrade(1, tradeForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(tradeRepository, times(0)).delete(trade);
        verify(tradeRepository, times(0)).save(tradeForUpdate);
        assertThat(tradeRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(tradeRepository.findAll().get(0).getTradeId()).isEqualTo(1);
        assertThat(tradeRepository.findAll().get(0).getRevisionDate()).isNull();
        assertThat(tradeRepository.findAll().get(0).getBook())
                .isEqualTo("book");
        assertThat(tradeRepository.findAll().get(1).getTradeId()).isEqualTo(2);
        assertThat(tradeRepository.findAll().get(1).getBook())
                .isEqualTo("book 2");
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Creation name different that original")
    public void givenBadCreationName_whenUpdate_thenReturnTrue() {
        // GIVEN
        when(tradeRepository.save(trade)).thenReturn(trade);
        when(tradeRepository.save(tradeTwo)).thenReturn(tradeTwo);
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeService.findAllTrade()).thenReturn(allTrades);

        Trade tradeForUpdate = new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.now().minusMonths(2), "security", "status",
                "trader", "benchmark", "book updated", "creationName OTHER",
                "REVISION NAME", "dealName", "dealType", "sourceListId",
                "side");

        // WHEN
        boolean result = tradeService.updateTrade(1, tradeForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(tradeRepository, times(0)).delete(trade);
        verify(tradeRepository, times(0)).save(tradeForUpdate);
        assertThat(tradeRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(tradeRepository.findAll().get(0).getTradeId()).isEqualTo(1);
        assertThat(tradeRepository.findAll().get(0).getRevisionDate()).isNull();
        assertThat(tradeRepository.findAll().get(0).getBook())
                .isEqualTo("book");
        assertThat(tradeRepository.findAll().get(1).getTradeId()).isEqualTo(2);
        assertThat(tradeRepository.findAll().get(1).getBook())
                .isEqualTo("book 2");
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Status size > max size(10)")
    public void givenBadSizeStatus_whenUpdate_thenReturnTrue() {
        // GIVEN
        when(tradeRepository.save(trade)).thenReturn(trade);
        when(tradeRepository.save(tradeTwo)).thenReturn(tradeTwo);
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeService.findAllTrade()).thenReturn(allTrades);

        Trade tradeForUpdate = new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.now().minusMonths(2), "security",
                "status max than 10", "trader", "benchmark", "book updated",
                "creationName", "REVISION NAME", "dealName", "dealType",
                "sourceListId", "side");

        // WHEN
        boolean result = tradeService.updateTrade(1, tradeForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(tradeRepository, times(0)).delete(trade);
        verify(tradeRepository, times(0)).save(tradeForUpdate);
        assertThat(tradeRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(tradeRepository.findAll().get(0).getTradeId()).isEqualTo(1);
        assertThat(tradeRepository.findAll().get(0).getRevisionDate()).isNull();
        assertThat(tradeRepository.findAll().get(0).getBook())
                .isEqualTo("book");
        assertThat(tradeRepository.findAll().get(1).getTradeId()).isEqualTo(2);
        assertThat(tradeRepository.findAll().get(1).getBook())
                .isEqualTo("book 2");
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Trade date after actual date")
    public void givenTradeDateInvalid_whenUpdate_thenReturnTrue() {
        // GIVEN
        when(tradeRepository.save(trade)).thenReturn(trade);
        when(tradeRepository.save(tradeTwo)).thenReturn(tradeTwo);
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeService.findAllTrade()).thenReturn(allTrades);

        Trade tradeForUpdate = new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.now().plusMonths(2), "security", "status",
                "trader", "benchmark", "book updated", "creationName",
                "REVISION NAME", "dealName", "dealType", "sourceListId",
                "side");

        // WHEN
        boolean result = tradeService.updateTrade(1, tradeForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(tradeRepository, times(0)).delete(trade);
        verify(tradeRepository, times(0)).save(tradeForUpdate);
        assertThat(tradeRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(tradeRepository.findAll().get(0).getTradeId()).isEqualTo(1);
        assertThat(tradeRepository.findAll().get(0).getRevisionDate()).isNull();
        assertThat(tradeRepository.findAll().get(0).getBook())
                .isEqualTo("book");
        assertThat(tradeRepository.findAll().get(1).getTradeId()).isEqualTo(2);
        assertThat(tradeRepository.findAll().get(1).getBook())
                .isEqualTo("book 2");
    }

}

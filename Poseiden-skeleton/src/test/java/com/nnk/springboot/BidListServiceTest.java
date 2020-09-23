package com.nnk.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.IBidListService;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application.properties")
//@Sql(scripts = "classpath:data_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BidListServiceTest {

    @Autowired
    public IBidListService bidListService;

    @MockBean
    private BidListRepository bidListRepository;

    private BidList bid, bidTwo, result;

    private List<BidList> allBidList;

    @BeforeEach
    public void setup() {
        allBidList = new ArrayList<>();
        bid = new BidList();
        bid.setBidListId(10);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);
        allBidList.add(bid);

        bidTwo = new BidList();
        bidTwo.setBidListId(20);
        bidTwo.setAccount("Account Test 2");
        bidTwo.setType("Type Test 2");
        bidTwo.setBidQuantity(20d);
        allBidList.add(bidTwo);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save bidList - OK - 1 saved")
    public void givenOneBid_whenSave_thenReturnSaved() {
        // GIVEN
        when(bidListService.saveBidList(bid)).thenReturn(bid);
        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result.getAccount()).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account Test");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType()).isEqualTo("Type Test");
        assertThat(result.getBidQuantity()).isEqualTo(10d);
        verify(bidListRepository, times(1)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save bidList - OK - Account entry max size (30)")
    public void givenMaxAccountSizeEntry_whenSaveBid_thenReturnSaved() {
        // GIVEN
        bid.setAccount("Type text with 30 size - Type ");
        when(bidListService.saveBidList(bid)).thenReturn(bid);
        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result.getAccount()).isNotNull();
        assertThat(result.getAccount())
                .isEqualTo("Type text with 30 size - Type0");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType()).isEqualTo("Type Test");
        assertThat(result.getBidQuantity()).isEqualTo(10d);
        verify(bidListRepository, times(1)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save bidList - OK - Type entry max size (30)")
    public void givenMaxTypeSizeEntry_whenSaveBid_thenReturnSaved() {
        // GIVEN
        bid.setType("Type text with 30 size - Type0");
        when(bidListService.saveBidList(bid)).thenReturn(bid);
        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result.getAccount()).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account Test");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType())
                .isEqualTo("Type text with 30 size - Type ");
        assertThat(result.getBidQuantity()).isEqualTo(10d);
        verify(bidListRepository, times(1)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Account - ERROR - Empty")
    public void givenEmptyAccount_whenSaveBid_thenReturnNull() {
        // GIVEN
        bid.setAccount("");

        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Account - ERROR - Null")
    public void givenNullAccount_whenSaveBid_thenReturnNull() {
        // GIVEN
        bid.setAccount(null);

        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Account - ERROR - More than max size (31)")
    public void givenAccountWithMoreThanMaxSizeEntry_whenSaveBid_thenReturnNull() {
        // GIVEN
        bid.setAccount("Type text with 31 size - Type t");

        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Type - ERROR - Empty")
    public void givenEmptyType_whenSaveBid_thenReturnNull() {
        // GIVEN
        bid.setType("");

        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Type - ERROR - More than max size (31)")
    public void givenTypeWithMoreThanMaxSizeEntry_whenSaveBid_thenReturnNull() {
        // GIVEN
        bid.setType("Type text with 31 size - Type t");

        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Type - ERROR - Null")
    public void givenNullType_whenSaveBid_thenReturnNull() {
        // GIVEN
        bid.setType(null);

        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Quantity - ERROR - Null bid quantity entry")
    public void givenNullBidQuantity_whenSaveBid_thenReturnNull() {
        // GIVEN
        bid.setBidQuantity(null);

        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Quantity - Ok - 0 bid quantity entry")
    public void givenNullZeroQuantity_whenSaveBid_thenReturnSaved() {
        // GIVEN
        bid.setBidQuantity(0d);
        when(bidListService.saveBidList(bid)).thenReturn(bid);
        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result.getAccount()).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account Test");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType()).isEqualTo("Type Test");
        assertThat(result.getBidQuantity()).isEqualTo(0d);
        verify(bidListRepository, times(1)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Quantity - Ok - 1599,87 bid quantity entry")
    public void givenValidFractionnalBidQuantity_whenSaveBid_thenReturnSaved() {
        // GIVEN
        bid.setBidQuantity(1599.87d);
        when(bidListService.saveBidList(bid)).thenReturn(bid);
        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result.getAccount()).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account Test");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType()).isEqualTo("Type Test");
        assertThat(result.getBidQuantity()).isEqualTo(1599.87d);
        verify(bidListRepository, times(1)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Quantity - ERROR - 1599,876 bid quantity entry")
    public void givenInvalidFractionnalBidQuantity_whenSaveBid_thenReturnNull() {
        // GIVEN
        bid.setBidQuantity(1599.876d);

        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(bid);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save - Quantity - ERROR - Negative number entry")
    public void givenNegativeBidQuantity_whenSaveBid_thenReturnNull() {
        // GIVEN
        bid.setBidQuantity(-1599.98d);

        // WHEN
        result = bidListService.saveBidList(bid);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(bid);
    }

//    @Test
//    @Tag("FIND")
//    @DisplayName("Find bid - OK")
//    public void aaaa() {
//
//        // GIVEN
//        bid = bidListRepository.save(bid);
//
//        // WHEN
//        List<BidList> resultList = bidListService.findAllBids();
//
//        // THEN
//        assertThat(bid.getBidListId()).isNotNull();
//        assertThat(bid.getBidQuantity()).isEqualTo(10d);
//        assertThat(resultList.size()).isEqualTo(1);
//
//    }

//    @Test
//    @Tag("CREATE")
//    @DisplayName("Create new bid - OK")
//    public void bidListTest() {
//        BidList bid = new BidList("Account Test", "Type Test", 10d);
//
//        // Save
//        bid = bidListRepository.save(bid);
//
//        assertThat(bid.getBidListId()).isNotNull();
//        assertThat(bid.getBidQuantity()).isEqualTo(10d);
//        // Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);
//
//        // Update
//        bid.setBidQuantity(20d);
//        bid = bidListRepository.save(bid);
//        assertThat(bid.getBidQuantity()).isEqualTo(20d);
//        // Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);
//
//        // Find
//        List<BidList> listResult = bidListRepository.findAll();
//        assertThat(listResult.size() > 0).isTrue();
//
//        // Delete
//        Integer id = bid.getBidListId();
//        bidListRepository.delete(bid);
//        Optional<BidList> bidList = bidListRepository.findById(id);
//        assertThat(bidList.isPresent()).isFalse();
//
//    }
//
//    @Test
//    @Tag("UPDATE")
//    @DisplayName("Update  bid - OK")
//    public void a() {
//        BidList bid = new BidList("Account Test", "Type Test", 10d);
//
//        // Save
//        bid = bidListRepository.save(bid);
//
//        assertThat(bid.getBidListId()).isNotNull();
//        assertThat(bid.getBidQuantity()).isEqualTo(10d);
//        // Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);
//
//        // Update
//        bid.setBidQuantity(20d);
//        bid = bidListRepository.save(bid);
//        assertThat(bid.getBidQuantity()).isEqualTo(20d);
//        // Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);
//
//    }
//
//    @Test
//    @Tag("DELETE")
//    @DisplayName("Delete bid - OK")
//    public void aa() {
//        BidList bid = new BidList("Account Test", "Type Test", 10d);
//
//        Integer id = bid.getBidListId();
//        bidListRepository.delete(bid);
//        Optional<BidList> bidList = bidListRepository.findById(id);
//        assertThat(bidList.isPresent()).isFalse();
//
//    }
}

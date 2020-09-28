package com.nnk.springboot.services;

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

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BidListServiceTest {

    @Autowired
    public IBidListService bidListService;

    @MockBean
    private BidListRepository bidListRepository;

    private BidList bid;
    private BidList bidTwo;
    private BidList result;

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
    @DisplayName("SaveBid - OK - 1 saved")
    public void givenOneBid_whenSave_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Account Test", "Type Test", 10d);

        // THEN
        assertThat(result.getAccount()).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account Test");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType()).isEqualTo("Type Test");
        assertThat(result.getBidQuantity()).isEqualTo(10d);
        verify(bidListRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - OK - Account entry max size (30)")
    public void givenMaxAccountSizeEntry_whenSaveBid_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Type text with 30 size ACCOUNT",
                "Type Test", 10d);

        // THEN
        assertThat(result.getAccount()).isNotNull();
        assertThat(result.getAccount())
                .isEqualTo("Type text with 30 size ACCOUNT");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType()).isEqualTo("Type Test");
        assertThat(result.getBidQuantity()).isEqualTo(10d);
        verify(bidListRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - OK - Type entry max size (30)")
    public void givenMaxTypeSizeEntry_whenSaveBid_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Account Test",
                "Type text with 30 size - Type0", 10d);

        // THEN
        assertThat(result.getAccount()).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account Test");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType())
                .isEqualTo("Type text with 30 size - Type0");
        assertThat(result.getBidQuantity()).isEqualTo(10d);
        verify(bidListRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Account - ERROR - Empty")
    public void givenEmptyAccount_whenSaveBid_thenReturnNull() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("", "Type", 10d);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Account - ERROR - Null")
    public void givenNullAccount_whenSaveBid_thenReturnNull() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid(null, "Type", 10d);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Account - ERROR - More than max size (31)")
    public void givenAccountWithMoreThanMaxSizeEntry_whenSaveBid_thenReturnNull() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Type text with 31 size - Type t",
                "Type", 10d);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Type - ERROR - Empty")
    public void givenEmptyType_whenSaveBid_thenReturnNull() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Account", "", 10d);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Type - ERROR - More than max size (31)")
    public void givenTypeWithMoreThanMaxSizeEntry_whenSaveBid_thenReturnNull() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Account",
                "Type text with 31 size - Type t", 10d);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Type - ERROR - Null")
    public void givenNullType_whenSaveBid_thenReturnNull() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Account", null, 10d);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Quantity - ERROR - Null bid quantity entry")
    public void givenNullBidQuantity_whenSaveBid_thenReturnNull() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Account", "Type", null);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Quantity - Ok - 0 bid quantity entry")
    public void givenNullZeroQuantity_whenSaveBid_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Account Test", "Type Test", 0d);

        // THEN
        assertThat(result.getAccount()).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account Test");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType()).isEqualTo("Type Test");
        assertThat(result.getBidQuantity()).isEqualTo(0d);
        verify(bidListRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Quantity - Ok - 1599,87 bid quantity entry")
    public void givenValidFractionnalBidQuantity_whenSaveBid_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Account Test", "Type Test", 1599.87d);

        // THEN
        assertThat(result.getAccount()).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account Test");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType()).isEqualTo("Type Test");
        assertThat(result.getBidQuantity()).isEqualTo(1599.87d);
        verify(bidListRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Quantity - ERROR - 1599,876 bid quantity entry")
    public void givenInvalidFractionnalBidQuantity_whenSaveBid_thenReturnNull() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Account Test", "Type Test", 1599.876d);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("SaveBid - Quantity - ERROR - Negative number entry")
    public void givenNegativeBidQuantity_whenSaveBid_thenReturnNull() {
        // GIVEN

        // WHEN
        result = bidListService.saveBid("Account Test", "Type Test", -1599.98d);

        // THEN
        assertThat(result).isNull();
        assertThat(bidListRepository.findAll().isEmpty());
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - OK")
    public void givenTwoBids_whenFindAll_thenReturnTwo() {
        // GIVEN
        when(bidListService.findAllBids()).thenReturn(allBidList);

        // WHEN
        List<BidList> resultList = bidListService.findAllBids();

        // THEN
        assertThat(resultList.size()).isNotNull();
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(bidListRepository.findAll().size()).isEqualTo(2);
        assertThat(bidListRepository.findAll().get(0).getBidQuantity())
                .isEqualTo(10d);
        assertThat(bidListRepository.findAll().get(1).getBidQuantity())
                .isEqualTo(20d);

    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - Ok - Empty list / 0 size")
    public void givenZeroBid_whenFindAll_thenReturnEmptyList() {
        // GIVEN
        allBidList.clear();
        when(bidListService.findAllBids()).thenReturn(allBidList);

        // WHEN
        List<BidList> resultList = bidListService.findAllBids();

        // THEN
        assertThat(resultList.size()).isNotNull();
        assertThat(resultList.size()).isEqualTo(0);
        assertThat(bidListRepository.findAll().size()).isEqualTo(0);
    }

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

//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save bidList - OK - 1 saved")
//  public void givenOneBid_whenSave_thenReturnSaved() {
//      // GIVEN
//      when(bidListService.saveBidList(bid)).thenReturn(bid);
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result.getAccount()).isNotNull();
//      assertThat(result.getAccount()).isEqualTo("Account Test");
//      assertThat(result.getType()).isNotNull();
//      assertThat(result.getType()).isEqualTo("Type Test");
//      assertThat(result.getBidQuantity()).isEqualTo(10d);
//      verify(bidListRepository, times(1)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save bidList - OK - Account entry max size (30)")
//  public void givenMaxAccountSizeEntry_whenSaveBid_thenReturnSaved() {
//      // GIVEN
//      bid.setAccount("Type text with 30 size - Type0");
//      when(bidListService.saveBidList(bid)).thenReturn(bid);
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result.getAccount()).isNotNull();
//      assertThat(result.getAccount())
//              .isEqualTo("Type text with 30 size - Type0");
//      assertThat(result.getType()).isNotNull();
//      assertThat(result.getType()).isEqualTo("Type Test");
//      assertThat(result.getBidQuantity()).isEqualTo(10d);
//      verify(bidListRepository, times(1)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save bidList - OK - Type entry max size (30)")
//  public void givenMaxTypeSizeEntry_whenSaveBid_thenReturnSaved() {
//      // GIVEN
//      bid.setType("Type text with 30 size - Type0");
//      when(bidListService.saveBidList(bid)).thenReturn(bid);
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result.getAccount()).isNotNull();
//      assertThat(result.getAccount()).isEqualTo("Account Test");
//      assertThat(result.getType()).isNotNull();
//      assertThat(result.getType())
//              .isEqualTo("Type text with 30 size - Type0");
//      assertThat(result.getBidQuantity()).isEqualTo(10d);
//      verify(bidListRepository, times(1)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Account - ERROR - Empty")
//  public void givenEmptyAccount_whenSaveBid_thenReturnNull() {
//      // GIVEN
//      bid.setAccount("");
//
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result).isNull();
//      assertThat(bidListRepository.findAll().isEmpty());
//      verify(bidListRepository, times(0)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Account - ERROR - Null")
//  public void givenNullAccount_whenSaveBid_thenReturnNull() {
//      // GIVEN
//      bid.setAccount(null);
//
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result).isNull();
//      assertThat(bidListRepository.findAll().isEmpty());
//      verify(bidListRepository, times(0)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Account - ERROR - More than max size (31)")
//  public void givenAccountWithMoreThanMaxSizeEntry_whenSaveBid_thenReturnNull() {
//      // GIVEN
//      bid.setAccount("Type text with 31 size - Type t");
//
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result).isNull();
//      assertThat(bidListRepository.findAll().isEmpty());
//      verify(bidListRepository, times(0)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Type - ERROR - Empty")
//  public void givenEmptyType_whenSaveBid_thenReturnNull() {
//      // GIVEN
//      bid.setType("");
//
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result).isNull();
//      assertThat(bidListRepository.findAll().isEmpty());
//      verify(bidListRepository, times(0)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Type - ERROR - More than max size (31)")
//  public void givenTypeWithMoreThanMaxSizeEntry_whenSaveBid_thenReturnNull() {
//      // GIVEN
//      bid.setType("Type text with 31 size - Type t");
//
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result).isNull();
//      assertThat(bidListRepository.findAll().isEmpty());
//      verify(bidListRepository, times(0)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Type - ERROR - Null")
//  public void givenNullType_whenSaveBid_thenReturnNull() {
//      // GIVEN
//      bid.setType(null);
//
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result).isNull();
//      assertThat(bidListRepository.findAll().isEmpty());
//      verify(bidListRepository, times(0)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Quantity - ERROR - Null bid quantity entry")
//  public void givenNullBidQuantity_whenSaveBid_thenReturnNull() {
//      // GIVEN
//      bid.setBidQuantity(null);
//
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result).isNull();
//      assertThat(bidListRepository.findAll().isEmpty());
//      verify(bidListRepository, times(0)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Quantity - Ok - 0 bid quantity entry")
//  public void givenNullZeroQuantity_whenSaveBid_thenReturnSaved() {
//      // GIVEN
//      bid.setBidQuantity(0d);
//      when(bidListService.saveBidList(bid)).thenReturn(bid);
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result.getAccount()).isNotNull();
//      assertThat(result.getAccount()).isEqualTo("Account Test");
//      assertThat(result.getType()).isNotNull();
//      assertThat(result.getType()).isEqualTo("Type Test");
//      assertThat(result.getBidQuantity()).isEqualTo(0d);
//      verify(bidListRepository, times(1)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Quantity - Ok - 1599,87 bid quantity entry")
//  public void givenValidFractionnalBidQuantity_whenSaveBid_thenReturnSaved() {
//      // GIVEN
//      bid.setBidQuantity(1599.87d);
//      when(bidListService.saveBidList(bid)).thenReturn(bid);
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result.getAccount()).isNotNull();
//      assertThat(result.getAccount()).isEqualTo("Account Test");
//      assertThat(result.getType()).isNotNull();
//      assertThat(result.getType()).isEqualTo("Type Test");
//      assertThat(result.getBidQuantity()).isEqualTo(1599.87d);
//      verify(bidListRepository, times(1)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Quantity - ERROR - 1599,876 bid quantity entry")
//  public void givenInvalidFractionnalBidQuantity_whenSaveBid_thenReturnNull() {
//      // GIVEN
//      bid.setBidQuantity(1599.876d);
//
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result).isNull();
//      assertThat(bidListRepository.findAll().isEmpty());
//      verify(bidListRepository, times(0)).save(bid);
//  }
//
//  @Test
//  @Tag("SAVE")
//  @DisplayName("Save - Quantity - ERROR - Negative number entry")
//  public void givenNegativeBidQuantity_whenSaveBid_thenReturnNull() {
//      // GIVEN
//      bid.setBidQuantity(-1599.98d);
//
//      // WHEN
//      result = bidListService.saveBidList(bid);
//
//      // THEN
//      assertThat(result).isNull();
//      assertThat(bidListRepository.findAll().isEmpty());
//      verify(bidListRepository, times(0)).save(bid);
//  }
//    
//    private BidList bid;
//    private BidList bidTwo;
//    private BidList result;
//
//    private List<BidList> allBidList;
//
//    @BeforeEach
//    public void setup() {
//        allBidList = new ArrayList<>();
//        bid = new BidList();
//        bid.setBidListId(10);
//        bid.setAccount("Account Test");
//        bid.setType("Type Test");
//        bid.setBidQuantity(10d);
//        allBidList.add(bid);
//
//        bidTwo = new BidList();
//        bidTwo.setBidListId(20);
//        bidTwo.setAccount("Account Test 2");
//        bidTwo.setType("Type Test 2");
//        bidTwo.setBidQuantity(20d);
//        allBidList.add(bidTwo);
//    }
}

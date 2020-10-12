package com.poseidon.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
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

import com.poseidon.domain.BidList;
import com.poseidon.repositories.BidListRepository;

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
    public void setUpPerTest() {
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
        assertThat(result.getCreationDate()).isNotNull();
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
        assertThat(result.getCreationDate()).isNotNull();
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
        assertThat(result.getCreationDate()).isNotNull();
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
        assertThat(resultList).isNotNull();
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
        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isEqualTo(0);
        assertThat(bidListRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - OK - Existing id")
    public void givenTwoBids_whenUpdateValidInfos_thenReturnSaved() {
        // GIVEN
        BidList bidInfosToUpdate = new BidList();
        bidInfosToUpdate.setBidListId(10);
        bidInfosToUpdate.setAccount("new account");
        bidInfosToUpdate.setType("new type");
        bidInfosToUpdate.setBidQuantity(150.98d);
        bidInfosToUpdate.setBook("book");
        bidInfosToUpdate.setRevisionName("revision");
        bidInfosToUpdate.setBidListDate(LocalDateTime.now().minusMonths(1));

        when(bidListRepository.save(bid)).thenReturn(bid);
        when(bidListRepository.save(bidInfosToUpdate))
                .thenReturn(bidInfosToUpdate);
        when(bidListRepository.findById(10)).thenReturn(Optional.of(bid));

        // WHEN
        result = bidListService.updateBid(bidInfosToUpdate, "Account Test",
                "Type Test");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBidListDate()).isNotNull();
        assertThat(result.getRevisionDate()).isNotNull();
        assertThat(result.getRevisionName()).isEqualTo("revision");
        assertThat(result.getBidListId()).isEqualTo(10);
        assertThat(result.getAccount()).isEqualTo("new account");
        assertThat(result.getType()).isEqualTo("new type");
        assertThat(result.getBook()).isEqualTo("book");
        assertThat(result.getBidQuantity()).isEqualTo(150.98d);
        verify(bidListRepository, times(1)).delete(bid);
        verify(bidListRepository, times(1)).save(result);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Invalid bid list date (after actual date)")
    public void givenInvalidBidListDate_whenUpdate_thenReturnSaved() {
        // GIVEN
        BidList bidInfosToUpdate = new BidList();
        bidInfosToUpdate.setBidListId(10);
        bidInfosToUpdate.setAccount("new account");
        bidInfosToUpdate.setType("new type");
        bidInfosToUpdate.setBidQuantity(150.98d);
        bidInfosToUpdate.setBook("book");
        bidInfosToUpdate.setRevisionName("revision");
        bidInfosToUpdate.setBidListDate(LocalDateTime.now().plusMonths(1));

        when(bidListRepository.save(bid)).thenReturn(bid);
        when(bidListRepository.save(bidInfosToUpdate))
                .thenReturn(bidInfosToUpdate);
        when(bidListRepository.findById(10)).thenReturn(Optional.of(bid));

        // WHEN
        result = bidListService.updateBid(bidInfosToUpdate, "Account Test",
                "Type Test");

        // THEN
        assertThat(result).isNull();
        verify(bidListRepository, times(0)).delete(bid);
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Null bidlist date")
    public void givenNullBidListDate_whenUpdate_thenReturnSaved() {
        // GIVEN
        BidList bidInfosToUpdate = new BidList();
        bidInfosToUpdate.setBidListId(10);
        bidInfosToUpdate.setAccount("new account");
        bidInfosToUpdate.setType("new type");
        bidInfosToUpdate.setBidQuantity(150.98d);
        bidInfosToUpdate.setBook("book");
        bidInfosToUpdate.setRevisionName("revision");
        bidInfosToUpdate.setBidListDate(null);

        when(bidListRepository.save(bid)).thenReturn(bid);
        when(bidListRepository.save(bidInfosToUpdate))
                .thenReturn(bidInfosToUpdate);
        when(bidListRepository.findById(10)).thenReturn(Optional.of(bid));

        // WHEN
        result = bidListService.updateBid(bidInfosToUpdate, "Account Test",
                "Type Test");

        // THEN
        assertThat(result).isNull();
        verify(bidListRepository, times(0)).delete(bid);
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - No revision name (empty)")
    public void givenemptyRevisionName_whenUpdate_thenReturnSaved() {
        // GIVEN
        BidList bidInfosToUpdate = new BidList();
        bidInfosToUpdate.setBidListId(10);
        bidInfosToUpdate.setAccount("new account");
        bidInfosToUpdate.setType("new type");
        bidInfosToUpdate.setBidQuantity(150.98d);
        bidInfosToUpdate.setBook("book");
        bidInfosToUpdate.setRevisionName("");
        bidInfosToUpdate.setBidListDate(LocalDateTime.now().minusMonths(1));

        when(bidListRepository.save(bid)).thenReturn(bid);
        when(bidListRepository.save(bidInfosToUpdate))
                .thenReturn(bidInfosToUpdate);
        when(bidListRepository.findById(10)).thenReturn(Optional.of(bid));

        // WHEN
        // 10 = bid id
        result = bidListService.updateBid(bidInfosToUpdate, "Account Test",
                "Type Test");

        // THEN
        assertThat(result).isNull();
        verify(bidListRepository, times(0)).delete(bid);
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Bad account")
    public void givenBadAccountEntry_whenUpdate_thenReturnSaved() {
        // GIVEN
        BidList bidInfosToUpdate = new BidList();
        bidInfosToUpdate.setBidListId(10);
        bidInfosToUpdate.setAccount("new account");
        bidInfosToUpdate.setType("new type");
        bidInfosToUpdate.setBidQuantity(150.98d);
        bidInfosToUpdate.setBook("book");
        bidInfosToUpdate.setRevisionName("revision");
        bidInfosToUpdate.setBidListDate(LocalDateTime.now().minusMonths(1));

        when(bidListRepository.save(bid)).thenReturn(bid);
        when(bidListRepository.save(bidInfosToUpdate))
                .thenReturn(bidInfosToUpdate);
        when(bidListRepository.findById(10)).thenReturn(Optional.of(bid));

        // WHEN
        // 10 = bid id
        result = bidListService.updateBid(bidInfosToUpdate, "Bad Account",
                "Type Test");

        // THEN
        assertThat(result).isNull();
        verify(bidListRepository, times(0)).delete(bid);
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Bad type")
    public void givenBadTypeEntry_whenUpdate_thenReturnSaved() {
        // GIVEN
        BidList bidInfosToUpdate = new BidList();
        bidInfosToUpdate.setBidListId(10);
        bidInfosToUpdate.setAccount("new account");
        bidInfosToUpdate.setType("new type");
        bidInfosToUpdate.setBidQuantity(150.98d);
        bidInfosToUpdate.setBook("book");
        bidInfosToUpdate.setRevisionName("revision");
        bidInfosToUpdate.setBidListDate(LocalDateTime.now().minusMonths(1));

        when(bidListRepository.save(bid)).thenReturn(bid);
        when(bidListRepository.save(bidInfosToUpdate))
                .thenReturn(bidInfosToUpdate);
        when(bidListRepository.findById(10)).thenReturn(Optional.of(bid));

        // WHEN
        // 10 = bid id
        result = bidListService.updateBid(bidInfosToUpdate, "Account Test",
                "BAD TYPE Test");

        // THEN
        assertThat(result).isNull();
        verify(bidListRepository, times(0)).delete(bid);
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - invalid info (bad quantity)")
    public void givenTwoBids_whenUpdateInvalidQuantity_thenReturnNull() {
        // GIVEN
        BidList bidInfosToUpdate = new BidList();
        bidInfosToUpdate.setBidListId(10);
        bidInfosToUpdate.setAccount("new account");
        bidInfosToUpdate.setType("new type");
        bidInfosToUpdate.setBidQuantity(150.985d);
        bidInfosToUpdate.setBook("book");

        when(bidListRepository.save(bid)).thenReturn(bid);
        // when(bidListRepository.save(bidInfosToUpdate))
        // .thenReturn(bidInfosToUpdate);
        when(bidListRepository.findById(10)).thenReturn(Optional.of(bid));

        // WHEN
        // 10 = bid id
        result = bidListService.updateBid(bidInfosToUpdate, "Account Test",
                "Type Test");

        // THEN
        assertThat(result).isNull();
        verify(bidListRepository, times(0)).delete(bid);
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - invalid id")
    public void givenTwoBids_whenSearchInvalidId_thenReturnNull() {
        // GIVEN
        BidList bidInfosToUpdate = new BidList();
        bidInfosToUpdate.setBidListId(19999);
        bidInfosToUpdate.setAccount("new account");
        bidInfosToUpdate.setType("new type");
        bidInfosToUpdate.setBidQuantity(150.985d);
        bidInfosToUpdate.setBook("book");

        when(bidListRepository.save(bid)).thenReturn(bid);
        when(bidListRepository.findById(10)).thenReturn(Optional.of(bid));

        // WHEN
        result = bidListService.updateBid(bidInfosToUpdate, "Account Test",
                "Type Test");

        // THEN
        assertThat(result).isNull();
        verify(bidListRepository, times(0)).delete(bid);
        verify(bidListRepository, times(0)).save(result);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - OK")
    public void givenBidInDb_whenDeleteWithCorrectValues_thenReturnTrue() {
        // GIVEN
        BidList bidToDelete = new BidList();
        bidToDelete.setBidListId(10);
        bidToDelete.setAccount("Account Test");
        bidToDelete.setType("Type Test");
        bidToDelete.setBidQuantity(15.90d);

        when(bidListRepository.save(bidToDelete)).thenReturn(bidToDelete);
        when(bidListRepository.findById(10))
                .thenReturn(Optional.of(bidToDelete));

        // WHEN
        // 10 = bidToDelete id
        boolean isDeleted = bidListService.deleteBid(10, "Account Test");

        // THEN
        assertThat(isDeleted).isTrue();
        verify(bidListRepository, times(1)).delete(bidToDelete);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Bad id")
    public void givenBidInDb_whenDeleteWithBadId_thenReturnFalse() {
        // GIVEN
        BidList bidToDelete = new BidList();
        bidToDelete.setBidListId(10);
        bidToDelete.setAccount("Account Test");
        bidToDelete.setType("Type Test");
        bidToDelete.setBidQuantity(15.90d);

        when(bidListRepository.save(bidToDelete)).thenReturn(bidToDelete);
        when(bidListRepository.findById(10))
                .thenReturn(Optional.of(bidToDelete));

        // WHEN
        boolean isDeleted = bidListService.deleteBid(1056, "Account Test");

        // THEN
        assertThat(isDeleted).isFalse();
        verify(bidListRepository, times(0)).delete(bidToDelete);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Bad account")
    public void givenBidInDb_whenDeleteWithBadAccount_thenReturnFalse() {
        // GIVEN
        BidList bidToDelete = new BidList();
        bidToDelete.setBidListId(10);
        bidToDelete.setAccount("Account Test");
        bidToDelete.setType("Type Test");
        bidToDelete.setBidQuantity(15.90d);

        when(bidListRepository.save(bidToDelete)).thenReturn(bidToDelete);
        when(bidListRepository.findById(10))
                .thenReturn(Optional.of(bidToDelete));

        // WHEN
        // 10 = bidToDelete id
        boolean isDeleted = bidListService.deleteBid(10, "Account BAD ENTRY");

        // THEN
        assertThat(isDeleted).isFalse();
        verify(bidListRepository, times(0)).delete(bidToDelete);
    }

    @Test
    @Tag("GET_by_ID")
    @DisplayName("Get by id - Ok")
    public void givenOneBid_whenGetById_thenReturnOk() {
        // GIVEN
        when(bidListRepository.save(bid)).thenReturn(bid);
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        // WHEN
        result = bidListService.getBidById(1);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account Test");
        assertThat(result.getType()).isEqualTo("Type Test");
    }

    @Test
    @Tag("GET_by_ID")
    @DisplayName("Get by id - Error - Bad id")
    public void givenBadBid_whenGetById_thenReturnNull() {

        when(bidListRepository.save(bid)).thenReturn(bid);

        assertThatNullPointerException().isThrownBy(() -> {
            bidListService.getBidById(99);
        });
    }

}

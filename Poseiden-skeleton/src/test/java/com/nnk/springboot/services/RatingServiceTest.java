package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class RatingServiceTest {

    @Autowired
    public IRatingService ratingService;

    @MockBean
    private RatingRepository ratingRepository;

    private Rating rating;
    private Rating ratingTwo;
    private Rating result;

    private List<Rating> allRatings;

    @BeforeEach
    public void setUpPerTest() {
        allRatings = new ArrayList<>();

        rating = new Rating();
        rating.setId(10);
        rating.setMoodysRating("Moodys");
        rating.setSandPRating("Sandprating");
        rating.setFitchRating("fitch");
        rating.setOrderNumber(10);
        allRatings.add(rating);

        ratingTwo = new Rating();
        ratingTwo.setId(20);
        ratingTwo.setMoodysRating("Moodys 2");
        ratingTwo.setSandPRating("Sandprating 2");
        ratingTwo.setFitchRating("fitch 2");
        ratingTwo.setOrderNumber(20);
        allRatings.add(ratingTwo);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save Rating - OK")
    public void givenOneRating_whenSave_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = ratingService.saveRating(rating);

        // THEN
        assertThat(result.getId()).isNotNull();
        assertThat(result.getMoodysRating()).isEqualTo("Moodys");
        assertThat(result.getSandPRating()).isEqualTo("Sandprating");
        assertThat(result.getFitchRating()).isEqualTo("fitch");
        assertThat(result.getOrderNumber()).isEqualTo(10);
        verify(ratingRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save Rating - Ok - Moodys Attribute lenght = max allow (125)")
    public void givenOneRatingWithAttributesEqualsThanSizeAllow_whenSave_thenReturnSaved() {
        // GIVEN
        rating.setMoodysRating(
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 12345");
        // WHEN
        result = ratingService.saveRating(rating);

        // THEN
        assertThat(result.getId()).isNotNull();
        assertThat(result.getMoodysRating()).isEqualTo(
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 12345");
        assertThat(result.getSandPRating()).isEqualTo("Sandprating");
        assertThat(result.getFitchRating()).isEqualTo("fitch");
        assertThat(result.getOrderNumber()).isEqualTo(10);
        verify(ratingRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save Rating - ERROR - Moodys Attribute lenght > max allow (125)")
    public void givenOneRatingWithAttributesMoreThanSizeAllow_whenSave_thenReturnNull() {
        // GIVEN
        rating.setMoodysRating(
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456");
        // WHEN
        result = ratingService.saveRating(rating);

        // THEN
        assertThat(result).isNull();
        verify(ratingRepository, times(0)).save(result);
    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - OK - 2 rating")
    public void givenTwoRating_whenFindAll_thenReturnTwoSizeList() {
        // GIVEN
        when(ratingService.findAllRating()).thenReturn(allRatings);

        // WHEN
        List<Rating> resultList = ratingService.findAllRating();

        // THEN
        assertThat(resultList.size()).isNotNull();
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(ratingRepository.findAll().size()).isEqualTo(2);
        assertThat(ratingRepository.findAll().get(0).getId()).isNotNull();
        assertThat(ratingRepository.findAll().get(0).getMoodysRating())
                .isEqualTo("Moodys");
        assertThat(ratingRepository.findAll().get(1).getId()).isNotNull();
        assertThat(ratingRepository.findAll().get(1).getMoodysRating())
                .isEqualTo("Moodys 2");
    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - Ok - Empty list / size = 0")
    public void givenZeroRating_whenFindAll_thenReturnEmptyList() {
        // GIVEN
        allRatings.clear();
        when(ratingService.findAllRating()).thenReturn(allRatings);

        // WHEN
        List<Rating> resultList = ratingService.findAllRating();

        // THEN
        assertThat(resultList.size()).isNotNull();
        assertThat(resultList.size()).isEqualTo(0);
        assertThat(ratingRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - OK - Existing id")
    public void givenValidId_whenUpdate_thenReturnTrue() {
        // GIVEN
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingRepository.save(ratingTwo)).thenReturn(ratingTwo);
        when(ratingRepository.findById(10)).thenReturn(Optional.of(rating));
        when(ratingService.findAllRating()).thenReturn(allRatings);

        Rating ratingForUpdate = new Rating("other moodys", "other sandp",
                "other fitch", 50);

        // WHEN
        boolean result = ratingService.updateRating(10, ratingForUpdate);

        // THEN
        assertThat(result).isTrue();
        verify(ratingRepository, times(1)).save(rating);
        assertThat(ratingRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(ratingRepository.findAll().get(0).getId()).isEqualTo(10);
        assertThat(ratingRepository.findAll().get(0).getMoodysRating())
                .isEqualTo("other moodys");
        assertThat(ratingRepository.findAll().get(1).getId()).isEqualTo(20);
        assertThat(ratingRepository.findAll().get(1).getMoodysRating())
                .isEqualTo("Moodys 2");
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Bad id")
    public void givenInvalidId_whenUpdate_thenReturnFalse() {
        // GIVEN
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingRepository.save(ratingTwo)).thenReturn(ratingTwo);
        when(ratingRepository.findById(10)).thenReturn(Optional.of(rating));
        when(ratingService.findAllRating()).thenReturn(allRatings);

        Rating ratingForUpdate = new Rating("other moodys", "other sandp",
                "other fitch", 50);

        // WHEN
        boolean result = ratingService.updateRating(11, ratingForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(ratingRepository, times(0)).save(rating);
        assertThat(ratingRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(ratingRepository.findAll().get(0).getId()).isEqualTo(10);
        assertThat(ratingRepository.findAll().get(0).getMoodysRating())
                .isEqualTo("Moodys");
        assertThat(ratingRepository.findAll().get(1).getId()).isEqualTo(20);
        assertThat(ratingRepository.findAll().get(1).getMoodysRating())
                .isEqualTo("Moodys 2");
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Invalid attributes - Order number size > max size (127)")
    public void givenInvalidAttributes_whenUpdate_thenReturnFalse() {
        // GIVEN
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingRepository.save(ratingTwo)).thenReturn(ratingTwo);
        when(ratingService.findAllRating()).thenReturn(allRatings);

        Rating ratingForUpdate = new Rating("other moodys", "other sandp",
                "other fitch", 900);

        // WHEN
        boolean result = ratingService.updateRating(10, ratingForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(ratingRepository, times(0)).save(rating);
        assertThat(ratingRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(ratingRepository.findAll().get(0).getId()).isEqualTo(10);
        assertThat(ratingRepository.findAll().get(0).getMoodysRating())
                .isEqualTo("Moodys");
        assertThat(ratingRepository.findAll().get(1).getId()).isEqualTo(20);
        assertThat(ratingRepository.findAll().get(1).getMoodysRating())
                .isEqualTo("Moodys 2");
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Invalid attributes size > max size (125)")
    public void givenInvalidAttributesSize_whenUpdate_thenReturnFalse() {
        // GIVEN
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingRepository.save(ratingTwo)).thenReturn(ratingTwo);
        when(ratingService.findAllRating()).thenReturn(allRatings);

        Rating ratingForUpdate = new Rating(
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456",
                "other sandp", "other fitch", 900);

        // WHEN
        boolean result = ratingService.updateRating(10, ratingForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(ratingRepository, times(0)).save(rating);
        assertThat(ratingRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(ratingRepository.findAll().get(0).getId()).isEqualTo(10);
        assertThat(ratingRepository.findAll().get(0).getMoodysRating())
                .isEqualTo("Moodys");
        assertThat(ratingRepository.findAll().get(1).getId()).isEqualTo(20);
        assertThat(ratingRepository.findAll().get(1).getMoodysRating())
                .isEqualTo("Moodys 2");
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - OK - Valid id")
    public void givenRatingInDb_whenDeleteWithValidId_thenReturnTrue() {
        // GIVEN
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingRepository.save(ratingTwo)).thenReturn(ratingTwo);
        when(ratingRepository.findById(10)).thenReturn(Optional.of(rating));

        // WHEN
        // 10 = rating id
        boolean isDeleted = ratingService.deleteRating(10);

        // THEN
        assertThat(isDeleted).isTrue();
        verify(ratingRepository, times(1)).delete(rating);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Unknow  id")
    public void givenRatingInDb_whenDeleteWithInvalidId_thenReturnFalse() {
        // GIVEN
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingRepository.save(ratingTwo)).thenReturn(ratingTwo);

        // WHEN
        // 10 or 20 are valid
        boolean isDeleted = ratingService.deleteRating(99);

        // THEN
        assertThat(isDeleted).isFalse();
        verify(ratingRepository, times(0)).delete(rating);
    }

}

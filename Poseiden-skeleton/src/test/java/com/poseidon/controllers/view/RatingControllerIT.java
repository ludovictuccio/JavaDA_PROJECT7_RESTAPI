package com.poseidon.controllers.view;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseidon.domain.Rating;
import com.poseidon.repositories.RatingRepository;
import com.poseidon.services.RatingService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RatingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        ratingRepository.deleteAll();
    }

    @Test
    @Tag("/rating/list")
    @DisplayName("Get - list")
    public void givenZeroRating_whenGetList_thenreturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rating/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andReturn();
    }

    @Test
    @Tag("/rating/add")
    @DisplayName("Get - add")
    public void givenRating_whenGetAdd_thenReturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rating/add")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("rating"))
                .andReturn();
    }

    @Test
    @Tag("/rating/validate")
    @DisplayName("Post - validate - OK")
    public void givenValidRating_whenValidate_thenReturnSaved()
            throws Exception {

        Rating rating = new Rating("moodys", "sand", "fitch", 1);
        String jsonContent = objectMapper.writeValueAsString(rating);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/rating/validate")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"))
                .andReturn();
    }

    @Test
    @Tag("/rating/update/{id}")
    @DisplayName("Post - update")
    public void givenOneRating_whenUpdate_thenReturnUpdated() throws Exception {

        Rating rating = new Rating("moodys", "sand", "fitch", 1);
        ratingService.saveRating(rating);

        String jsonContent = objectMapper.writeValueAsString(rating);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/rating/update/1")
                        .contentType(MediaType.ALL).content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"))
                .andReturn();
    }

    @Test
    @Tag("/rating/delete")
    @DisplayName("Delete - OK")
    public void givenRating_whenDelete_thenReturnDeleted() throws Exception {

        Rating rating = new Rating("moodys", "sand", "fitch", 1);
        ratingService.saveRating(rating);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rating/delete/1")
                        .contentType(MediaType.ALL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"))
                .andReturn();
    }

    @Test
    @Tag("/rating/delete")
    @DisplayName("Delete - Error - Bad id")
    public void givenRating_whenDeleteBadId_thenReturnNotDeleted()
            throws Exception {

        Rating rating = new Rating("moodys", "sand", "fitch", 1);
        ratingService.saveRating(rating);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rating/delete/999")
                        .contentType(MediaType.ALL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"))
                .andReturn();
    }

}

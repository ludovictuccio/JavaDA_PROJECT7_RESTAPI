package com.poseidon.controllers.api;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseidon.domain.Rating;
import com.poseidon.repositories.RatingRepository;
import com.poseidon.services.IRatingService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RatingControllerApiRestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IRatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        ratingRepository.deleteAll();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK")
    public void givenMaxValue_whenCreate_thenReturnCreated() throws Exception {

        Rating ratingToCreate = new Rating("moodys", "sandprating", "fitch",
                127);
        String jsonContent = objectMapper.writeValueAsString(ratingToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/rating/add")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK - Empty attributes")
    public void givenEmptyAttributes_whenCreate_thenReturnCreated()
            throws Exception {

        Rating ratingToCreate = new Rating("moodys", "", "", 12);
        String jsonContent = objectMapper.writeValueAsString(ratingToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/rating/add")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Order number already existing")
    public void givenOrderAlreadyExisting_whenCreate_thenReturnBadRequest()
            throws Exception {
        ratingService
                .saveRating(new Rating("moodys", "sandprating", "fitch", 10));

        Rating ratingToCreate = new Rating("moodys", "sandprating", "fitch",
                10);
        String jsonContent = objectMapper.writeValueAsString(ratingToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/rating/add")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Moddys size > max size (125)")
    public void givenSupreriorAttributesThanMaxValue_whenCreate_thenReturnBadRequest()
            throws Exception {

        Rating ratingToCreate = new Rating(
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456",
                "sandprating", "fitch", 15);
        String jsonContent = objectMapper.writeValueAsString(ratingToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/rating/add")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 2 rating in db")
    public void givenTwoRatingInDb_whenGet_thenReturnListWithTwoRating()
            throws Exception {
        ratingService
                .saveRating(new Rating("moodys", "sandprating", "fitch", 10));
        ratingService.saveRating(
                new Rating("moodys 2", "sandprating 2", "fitch 2", 20));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/rating/get")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 0 rating in db")
    public void givenZeroRatingInDb_whenGet_thenReturnEmptyList()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/rating/get")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("PUT")
    @DisplayName("Put - ERROR - Invalid id")
    public void givenRatingToUpdate_whenInvalidId_thenReturnBadRequest()
            throws Exception {
        ratingService
                .saveRating(new Rating("moodys", "sandprating", "fitch", 10));
        ratingRepository.findAll().get(0).setId(1);

        Rating ratingForUpdate = new Rating("other moodys", "other sandprating",
                "other fitch", 10);
        String jsonContent = objectMapper.writeValueAsString(ratingForUpdate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/rating/update")
                        .contentType(APPLICATION_JSON).param("id", "120")
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("PUT")
    @DisplayName("Put - ERROR - Order number changed")
    public void givenRatingToUpdate_whenOrderNumberChanged_thenReturnBadRequest()
            throws Exception {
        ratingService
                .saveRating(new Rating("moodys", "sandprating", "fitch", 10));
        ratingRepository.findAll().get(0).setId(1);

        Rating ratingForUpdate = new Rating("other moodys", "other sandprating",
                "other fitch", 99);
        String jsonContent = objectMapper.writeValueAsString(ratingForUpdate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/rating/update")
                        .contentType(APPLICATION_JSON).param("id", "10")
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Unknow id")
    public void givenZeroRating_whenDeleteInvalidId_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/rating/delete")
                        .contentType(APPLICATION_JSON).param("id", "99"))
                .andExpect(status().isBadRequest());
    }

}

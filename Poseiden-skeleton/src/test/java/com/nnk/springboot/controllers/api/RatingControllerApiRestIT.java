package com.nnk.springboot.controllers.api;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
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
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.IRatingService;

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

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    public void setUpPerTest() {
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
    @DisplayName("Create - ERROR - Order number > max size (127)")
    public void givenSuperiorThanMaxValue_whenCreate_thenReturnBadRequest()
            throws Exception {

        Rating ratingToCreate = new Rating("moodys", "sandprating", "fitch",
                200);
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
                "sandprating", "fitch", 200);
        String jsonContent = objectMapper.writeValueAsString(ratingToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/rating/add")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }
}

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
import com.nnk.springboot.repositories.CurvePointRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CurvePointControllerApiRestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    public void setUpPerTest() {
        curvePointRepository.deleteAll();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK")
    public void a() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "15")
                        .param("term", "150").param("value", "1500"))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Negative id")
    public void aa() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "-15")
                        .param("term", "150").param("value", "1500"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Letter for id")
    public void aaa() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "a")
                        .param("term", "150").param("value", "1500"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Character for id")
    public void aaaa() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "/")
                        .param("term", "150").param("value", "1500"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Character for term")
    public void aaaaa() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "15")
                        .param("term", "*").param("value", "1500"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Letter for term")
    public void aaaaaa() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "15")
                        .param("term", "p").param("value", "1500"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Letter for value")
    public void aaaaaaa() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "15")
                        .param("term", "150").param("value", "l"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Character for value")
    public void aaaaaaaa() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "15")
                        .param("term", "150").param("value", "-"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }
}

package org.example.challengestationspaths.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.challengestationspaths.dto.request.PathDTO;
import org.example.challengestationspaths.service.CustomPathService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = PathController.class)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
public class PathControllerTest {

    @InjectMocks
    PathController pathController;

    @MockBean
    private CustomPathService pathService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    public void testCreatePath() throws Exception {
        PathDTO pathDTO = new PathDTO(3L, 2L, 50.0);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/paths/2")
                .content(objectMapper.writeValueAsString(pathDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    public void testGetShortestPath() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/paths/1/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}

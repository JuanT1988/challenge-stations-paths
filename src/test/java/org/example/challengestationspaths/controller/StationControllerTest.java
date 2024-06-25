package org.example.challengestationspaths.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.challengestationspaths.dto.request.StationDTO;
import org.example.challengestationspaths.service.CustomStationService;
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

@WebMvcTest(controllers = StationController.class)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters=false)
public class StationControllerTest {

    @InjectMocks
    StationController stationController;

    @MockBean
    private CustomStationService stationService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    public void testCreateStation() throws Exception {
        StationDTO stationDTO = new StationDTO();
        stationDTO.setName("Dinamarca");
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/stations/3")
                .content(objectMapper.writeValueAsString(stationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    public void testGetAllStations() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/stations")
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}

package sysman.techassessment.infrastructure.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import sysman.techassessment.application.usecase.CitySPI;
import sysman.techassessment.application.usecase.MaterialSPI;
import sysman.techassessment.domain.model.City;
import sysman.techassessment.domain.model.Material;
import sysman.techassessment.domain.model.MaterialState;
import sysman.techassessment.infrastructure.adapter.in.web.dto.CityDto;
import sysman.techassessment.infrastructure.adapter.in.web.dto.MaterialRequestDto;
import sysman.techassessment.infrastructure.adapter.in.web.dto.MaterialResponseDto;
import sysman.techassessment.infrastructure.adapter.in.web.mapper.MaterialMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaterialController.class)
class MaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MaterialSPI materialSPI;

    @MockitoBean
    private CitySPI citySPI;

    @MockitoBean
    private MaterialMapper materialMapper;

    @Test
    void testRegisterMaterialReturnsCreated() throws Exception {
        MaterialRequestDto requestDto = new MaterialRequestDto(
                "Ladrillo",
                "Ladrillo de arcilla",
                "Construccion",
                new BigDecimal("1200"),
                null,
                null,
                "BOG",
                null
        );

        City city = new City();
        city.setCode("BOG");
        city.setName("Bogotá");
        
        CityDto cityDto = new CityDto("BOG", "Bogotá");

        Material materialToRegister = new Material();
        materialToRegister.setName("Ladrillo");

        Material registeredMaterial = new Material();
        registeredMaterial.setId(1);
        registeredMaterial.setName("Ladrillo");
        registeredMaterial.setState(MaterialState.AVAILABLE);
        registeredMaterial.setBuyDate(LocalDateTime.now());

        MaterialResponseDto responseDto = new MaterialResponseDto(
                1,
                "Ladrillo",
                "Ladrillo de arcilla",
                "Construccion",
                new BigDecimal("1200"),
                registeredMaterial.getBuyDate(),
                null,
                cityDto,
                MaterialState.AVAILABLE
        );

        when(citySPI.search("BOG")).thenReturn(city);
        when(materialMapper.toDomain(any(MaterialRequestDto.class))).thenReturn(materialToRegister);
        when(materialSPI.register(any(Material.class))).thenReturn(registeredMaterial);
        when(materialMapper.toDto(any(Material.class), any(City.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ladrillo"))
                .andExpect(jsonPath("$.state").value("AVAILABLE"))
                .andExpect(jsonPath("$.city.code").value("BOG"));
    }

    @Test
    void testRegisterMaterialValidatesInput() throws Exception {
        // Missing name and type, which are @NotBlank
        MaterialRequestDto invalidRequest = new MaterialRequestDto(
                "",
                "Ladrillo de arcilla",
                "",
                new BigDecimal("1200"),
                null,
                null,
                "BOG",
                null
        );

        mockMvc.perform(post("/api/v1/materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}

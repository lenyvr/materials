package sysman.techassessment.infrastructure.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import sysman.techassessment.application.usecase.CitySPI;
import sysman.techassessment.application.usecase.MaterialSPI;
import sysman.techassessment.domain.model.City;
import sysman.techassessment.domain.model.Material;
import sysman.techassessment.domain.model.MaterialState;
import sysman.techassessment.domain.model.PageResult;
import sysman.techassessment.infrastructure.adapter.in.web.dto.CityDto;
import sysman.techassessment.infrastructure.adapter.in.web.dto.MaterialRequestDto;
import sysman.techassessment.infrastructure.adapter.in.web.dto.MaterialResponseDto;
import sysman.techassessment.infrastructure.adapter.in.web.mapper.MaterialMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void testDeleteMaterialReturnsOk() throws Exception {
        mockMvc.perform(delete("/api/v1/materials/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Material with id 1 deleted successfully"));
    }

    @Test
    void testUpdateMaterialReturnsOk() throws Exception {
        MaterialRequestDto updateReq = new MaterialRequestDto(
                "Ladrillo update",
                null,
                "Construccion",
                new BigDecimal("1500"),
                null,
                null,
                "BOG",
                null
        );

        Material materialToUpdate = new Material();
        materialToUpdate.setName("Ladrillo update");

        Material updatedMaterial = new Material();
        updatedMaterial.setId(1);
        updatedMaterial.setName("Ladrillo update");
        updatedMaterial.setCityCode("BOG");

        City city = new City();
        city.setCode("BOG");
        city.setName("Bogotá");

        CityDto cityDto = new CityDto("BOG", "Bogotá");

        MaterialResponseDto responseDto = new MaterialResponseDto(
                1,
                "Ladrillo update",
                null,
                "Construccion",
                new BigDecimal("1500"),
                null,
                null,
                cityDto,
                MaterialState.AVAILABLE
        );

        when(materialMapper.toDomain(any(MaterialRequestDto.class))).thenReturn(materialToUpdate);
        when(materialSPI.update(eq(1), any(Material.class))).thenReturn(updatedMaterial);
        when(citySPI.search("BOG")).thenReturn(city);
        when(materialMapper.toDto(any(Material.class), any(City.class))).thenReturn(responseDto);

        mockMvc.perform(patch("/api/v1/materials/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ladrillo update"));
    }

    @Test
    void testListMaterialsReturnsOk() throws Exception {
        Material material = new Material();
        material.setId(1);
        material.setName("Ladrillo");
        material.setCityCode("BOG");
        material.setState(MaterialState.AVAILABLE);
        PageResult<Material> pageResult = new PageResult<>(Collections.singletonList(material), 1, 1, 0, 10);

        City city = new City();
        city.setName("Bogotá");

        CityDto cityDto = new CityDto("BOG", "Bogotá");
        MaterialResponseDto responseDto = new MaterialResponseDto(
                1,
                "Ladrillo",
                "Ladrillo de arcilla",
                "Construccion",
                new BigDecimal("1200"),
                null,
                null,
                cityDto,
                MaterialState.AVAILABLE
        );

        when(citySPI.search("BOG")).thenReturn(city);
        when(citySPI.searchByName(any())).thenReturn(null);
        when(materialSPI.listMaterials(any(), any(), any(), any(), eq(0), eq(10))).thenReturn(pageResult);
        when(materialMapper.toDto(any(Material.class), any(City.class))).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/materials")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Ladrillo"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}

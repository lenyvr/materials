package sysman.techassessment.infrastructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sysman.techassessment.application.usecase.CitySPI;
import sysman.techassessment.application.usecase.MaterialSPI;
import sysman.techassessment.domain.model.City;
import sysman.techassessment.domain.model.Material;
import sysman.techassessment.domain.model.MaterialState;
import sysman.techassessment.infrastructure.adapter.in.web.dto.*;
import sysman.techassessment.infrastructure.adapter.in.web.mapper.MaterialMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/materials")
public class MaterialController {

    private final MaterialSPI materialSPI;
    private final MaterialMapper materialMapper;
    private final CitySPI citySPI;

    public MaterialController(MaterialSPI materialSPI, MaterialMapper materialMapper, CitySPI citySPI) {
        this.materialSPI = materialSPI;
        this.materialMapper = materialMapper;
        this.citySPI = citySPI;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register new Material")
    public MaterialResponseDto registerMaterial(@Valid @RequestBody MaterialRequestDto requestDto) {
        City city = citySPI.search(requestDto.cityCode());
        Material material = materialMapper.toDomain(requestDto);
        Material registeredMaterial = materialSPI.register(material);
        return materialMapper.toDto(registeredMaterial, city);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteMaterialResponseDTO deactivateAccount(@PathVariable Long id) {
        return new  DeleteMaterialResponseDTO("Material with id deleted successfully");
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MaterialResponseDto updateAccount(@PathVariable Long id,
                                                  @RequestBody UpdateMaterialRequestDTO request) {
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponseDTO<MaterialListItemDTO> listMaterial(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate buyDate,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return null;
    }


}

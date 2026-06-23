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
import sysman.techassessment.infrastructure.adapter.in.web.dto.*;
import sysman.techassessment.infrastructure.adapter.in.web.mapper.MaterialMapper;
import sysman.techassessment.domain.model.PageResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Operation(summary = "Deactivate an existing Material")
    public DeleteMaterialResponseDTO deactivateAccount(@PathVariable Integer id) {
        materialSPI.delete(id);
        return new DeleteMaterialResponseDTO("Material with id " + id + " deleted successfully");
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing Material")
    public MaterialResponseDto updateAccount(@PathVariable Integer id,
                                             @Valid @RequestBody MaterialRequestDto request) {
        City city = citySPI.search(request.cityCode());
        Material materialUpdate = materialMapper.toDomain(request);
        Material updatedMaterial = materialSPI.update(id, materialUpdate);
        return materialMapper.toDto(updatedMaterial, city);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List Materials with pagination and filters")
    public PagedResponseDTO<MaterialResponseDto> listMaterial(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startBuyDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endBuyDate,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        City cityFound = citySPI.searchByName(city);
        PageResult<Material> result = materialSPI.listMaterials(type, startBuyDate, endBuyDate
                , Objects.isNull(cityFound)?null:cityFound.getCode(), page, size);

        List<MaterialResponseDto> responseDtoList = result.getContent().stream().map(m -> {
            City c = citySPI.search(m.getCityCode());
            return materialMapper.toDto(m, c);
        }).collect(Collectors.toList());

        return new PagedResponseDTO<>(
                responseDtoList,
                result.getTotalElements(),
                result.getTotalPages(),
                result.getCurrentPage(),
                result.getPageSize()
        );
    }


}

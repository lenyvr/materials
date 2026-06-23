package sysman.techassessment.infrastructure.adapter.in.web.mapper;

import org.springframework.stereotype.Component;
import sysman.techassessment.domain.model.City;
import sysman.techassessment.domain.model.Material;
import sysman.techassessment.infrastructure.adapter.in.web.dto.CityDto;
import sysman.techassessment.infrastructure.adapter.in.web.dto.DeleteMaterialResponseDTO;
import sysman.techassessment.infrastructure.adapter.in.web.dto.MaterialRequestDto;
import sysman.techassessment.infrastructure.adapter.in.web.dto.MaterialResponseDto;
import sysman.techassessment.infrastructure.adapter.out.persistence.entity.MaterialEntity;

import java.util.Objects;

@Component
public class MaterialMapper {

    public Material toDomain(MaterialRequestDto dto) {
        if (dto == null) return null;
        return new Material(
                null,
                dto.name(),
                dto.description(),
                dto.type(),
                dto.price(),
                dto.buyDate(),
                dto.soldDate(),
                dto.cityCode(),
                dto.state());
    }

    public MaterialResponseDto toDto(Material materialDomain, City cityDomain) {
        if (materialDomain == null) return null;

        CityDto cityDto;
        if (Objects.isNull(cityDomain)) {
            cityDto = new CityDto(materialDomain.getCityCode(), "NOT FOUND");
        } else {
            cityDto = new CityDto(cityDomain.getCode(), cityDomain.getName());
        }
        return new MaterialResponseDto(
                materialDomain.getId(),
                materialDomain.getName(),
                materialDomain.getDescription(),
                materialDomain.getType(),
                materialDomain.getPrice(),
                materialDomain.getBuyDate(),
                materialDomain.getSoldDate(),
                cityDto,
                materialDomain.getState());
    }

    public MaterialEntity toEntity(Material domain) {
        if (domain == null) return null;
        MaterialEntity entity = new MaterialEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setType(domain.getType());
        entity.setPrice(domain.getPrice());
        entity.setBuyDate(domain.getBuyDate());
        entity.setSoldDate(domain.getSoldDate());
        entity.setCityCode(domain.getCityCode());
        entity.setState(domain.getState());
        return entity;
    }

    public Material toDomain(MaterialEntity entity) {
        if (entity == null) return null;
        Material material = new Material();
        material.setId(entity.getId());
        material.setName(entity.getName());
        material.setDescription(entity.getDescription());
        material.setType(entity.getType());
        material.setPrice(entity.getPrice());
        material.setBuyDate(entity.getBuyDate());
        material.setSoldDate(entity.getSoldDate());
        material.setCityCode(entity.getCityCode());
        material.setState(entity.getState());
        return material;
    }

    public DeleteMaterialResponseDTO toDeleteDto(Material materialDomain) {
        return  new DeleteMaterialResponseDTO(String.format("Material with name %s deleted successfully", materialDomain.getName()));
    }
}

package sysman.techassessment.infrastructure.adapter.in.web.mapper;

import org.springframework.stereotype.Component;
import sysman.techassessment.domain.model.City;
import sysman.techassessment.infrastructure.adapter.in.web.dto.CityDto;
import sysman.techassessment.infrastructure.adapter.out.persistence.entity.CityEntity;

import java.util.Objects;

@Component
public class CityMapper {
    public CityDto toDTO(City model) {
        return new CityDto(
                model.getCode(),
                model.getName()
        );
    }

    public City toDomainFromDto(CityDto dto) {
        return new City(
                null,
                dto.name(),
                dto.code()
        );
    }

    public City toDomainFromEntity(CityEntity entity) {
        if(Objects.isNull(entity)) return null;
        return new City(
                entity.getDepartmentCode(),
                entity.getName(),
                entity.getCode()
        );
    }
}

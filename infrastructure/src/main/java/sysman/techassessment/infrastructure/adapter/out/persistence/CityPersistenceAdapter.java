package sysman.techassessment.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;
import sysman.techassessment.domain.model.City;
import sysman.techassessment.domain.port.out.CityIRepository;
import sysman.techassessment.infrastructure.adapter.in.web.mapper.CityMapper;
import sysman.techassessment.infrastructure.adapter.out.persistence.entity.CityEntity;
import sysman.techassessment.infrastructure.adapter.out.persistence.repository.CityRepository;

import java.util.Objects;

@Component
public class CityPersistenceAdapter implements CityIRepository {

    private final CityRepository cityRepository;
    private final CityMapper mapper;

    public CityPersistenceAdapter(CityRepository cityRepository, CityMapper mapper) {
        this.cityRepository = cityRepository;
        this.mapper = mapper;
    }

    @Override
    public City getCity(String code) {
        if (Objects.isNull(code) || code.trim().isEmpty()) return null;
        CityEntity cityEntity = cityRepository.findById(code).orElse(null);
        return mapper.toDomainFromEntity(cityEntity);
    }
}

package sysman.techassessment.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;
import sysman.techassessment.domain.model.Material;
import sysman.techassessment.domain.port.out.MaterialIRepository;
import sysman.techassessment.infrastructure.adapter.in.web.mapper.MaterialMapper;
import sysman.techassessment.infrastructure.adapter.out.persistence.entity.MaterialEntity;
import sysman.techassessment.infrastructure.adapter.out.persistence.repository.MaterialRepository;

import java.util.Objects;

@Component
public class MaterialIPersistenceAdapter implements MaterialIRepository {

    private final MaterialRepository repository;
    private final MaterialMapper mapper;

    public MaterialIPersistenceAdapter(MaterialRepository repository, MaterialMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Material save(Material material) {
        MaterialEntity entity = mapper.toEntity(material);
        MaterialEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Material findMaterial(String name) {
        if (Objects.isNull(name) || name.trim().isEmpty()) return null;
        MaterialEntity materialEntity = repository.findByName(name).orElse(null);
        return mapper.toDomain(materialEntity);
    }

    @Override
    public Material findById(Integer id) {
        if (id == null) return null;
        MaterialEntity materialEntity = repository.findById(id).orElse(null);
        return mapper.toDomain(materialEntity);
    }
}

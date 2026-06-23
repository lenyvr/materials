package sysman.techassessment.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;
import sysman.techassessment.domain.model.Material;
import sysman.techassessment.domain.port.out.MaterialIRepository;
import sysman.techassessment.infrastructure.adapter.in.web.mapper.MaterialMapper;
import sysman.techassessment.infrastructure.adapter.out.persistence.entity.MaterialEntity;
import sysman.techassessment.infrastructure.adapter.out.persistence.repository.MaterialRepository;
import sysman.techassessment.domain.model.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public PageResult<Material> listMaterials(String type, LocalDateTime startDate, LocalDateTime endDate, String cityCode, int page, int size) {
        Page<MaterialEntity> entityPage = repository.searchMaterials(type, startDate, endDate, cityCode, PageRequest.of(page, size));
        return new PageResult<>(
                entityPage.getContent().stream().map(mapper::toDomain).collect(Collectors.toList()),
                entityPage.getTotalElements(),
                entityPage.getTotalPages(),
                entityPage.getNumber(),
                entityPage.getSize()
        );
    }
}

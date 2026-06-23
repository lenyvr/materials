package sysman.techassessment.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sysman.techassessment.infrastructure.adapter.out.persistence.entity.MaterialEntity;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, Integer> {
    Optional<MaterialEntity> findByName(String name);
}

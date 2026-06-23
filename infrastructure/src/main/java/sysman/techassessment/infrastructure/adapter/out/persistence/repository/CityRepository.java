package sysman.techassessment.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sysman.techassessment.infrastructure.adapter.out.persistence.entity.CityEntity;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, String> {
}

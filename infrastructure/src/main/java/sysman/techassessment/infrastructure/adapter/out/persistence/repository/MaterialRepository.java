package sysman.techassessment.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sysman.techassessment.infrastructure.adapter.out.persistence.entity.MaterialEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, Integer> {
    Optional<MaterialEntity> findByName(String name);

    @Query("SELECT m FROM MaterialEntity m WHERE " +
           "m.state != 'INACTIVE' AND " +
           "(:type IS NULL OR lower(m.type) like :type) AND " +
           "(CAST(:startDate AS timestamp) IS NULL OR m.buyDate >= :startDate) AND " +
           "(CAST(:endDate AS timestamp) IS NULL OR m.buyDate <= :endDate) AND " +
           "(:cityCode IS NULL OR lower(m.cityCode) like :cityCode)")
    Page<MaterialEntity> searchMaterials(
            @Param("type") String type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("cityCode") String cityCode,
            Pageable pageable);
}

package sysman.techassessment.domain.port.out;

import sysman.techassessment.domain.model.Material;
import sysman.techassessment.domain.model.PageResult;

import java.time.LocalDateTime;

public interface MaterialIRepository {
    Material save(Material material);
    Material findMaterial(String name);
    Material findById(Integer id);
    PageResult<Material> listMaterials(String type, LocalDateTime startDate, LocalDateTime endDate, String cityCode, int page, int size);
}

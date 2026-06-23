package sysman.techassessment.application.usecase;

import sysman.techassessment.domain.model.Material;

public interface MaterialSPI {
    Material register(Material material);
    Material update(Integer id, Material material);
    Material delete(Integer id);
}

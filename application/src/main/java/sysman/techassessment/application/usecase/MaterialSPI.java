package sysman.techassessment.application.usecase;

import sysman.techassessment.domain.model.Material;
import sysman.techassessment.domain.model.PageResult;
import java.time.LocalDate;

public interface MaterialSPI {
    Material register(Material material);
    Material update(Integer id, Material material);
    void delete(Integer id);
    PageResult<Material> listMaterials(String type, LocalDate startBuyDate, LocalDate endBuyDate, String cityCode
            , int page, int size);
}

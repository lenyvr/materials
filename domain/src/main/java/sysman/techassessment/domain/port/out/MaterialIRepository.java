package sysman.techassessment.domain.port.out;

import sysman.techassessment.domain.model.Material;

public interface MaterialIRepository {
    Material save(Material material);
    Material findMaterial(String name);
}

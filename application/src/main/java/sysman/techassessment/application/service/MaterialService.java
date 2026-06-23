package sysman.techassessment.application.service;

import sysman.techassessment.application.usecase.MaterialSPI;
import sysman.techassessment.domain.exception.MaterialAlreadyExists;
import sysman.techassessment.domain.model.Material;
import sysman.techassessment.domain.model.MaterialState;
import sysman.techassessment.domain.port.out.MaterialIRepository;

import java.time.LocalDateTime;
import java.util.Objects;

public class MaterialService implements MaterialSPI {

    private final MaterialIRepository materialIRepository;

    public MaterialService(MaterialIRepository materialIRepository) {
        this.materialIRepository = materialIRepository;
    }

    @Override
    public Material register(Material material) {
        fillDefaults(material);
        Material materialFound = materialIRepository.findMaterial(material.getName());
        if(Objects.nonNull(materialFound)) {
            validateExistence(materialFound);
            material.setId(materialFound.getId());
        }
        return materialIRepository.save(material);
    }

    private void validateExistence(Material material) {
        if(!MaterialState.INACTIVE.equals(material.getState())){
            throw new MaterialAlreadyExists(String.format("El material %s que intentas registrar, ya existe.", material.getName()));
        }
    }

    private void fillDefaults(Material material) {
        if (material.getBuyDate() == null) {
            material.setBuyDate(LocalDateTime.now());
        }

        if (material.getState() == null) {
            material.setState(MaterialState.AVAILABLE);
        }
    }

}

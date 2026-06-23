package sysman.techassessment.application.service;

import sysman.techassessment.application.usecase.MaterialSPI;
import sysman.techassessment.domain.exception.BusinessDomainException;
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

    @Override
    public Material update(Integer id, Material materialUpdate) {
        Material existing = materialIRepository.findById(id);
        if (existing == null || MaterialState.INACTIVE.equals(existing.getState())) {
            throw new BusinessDomainException("El material a actualizar no existe.");
        }

        if (materialUpdate.getName() != null) existing.setName(materialUpdate.getName());
        if (materialUpdate.getDescription() != null) existing.setDescription(materialUpdate.getDescription());
        if (materialUpdate.getType() != null) existing.setType(materialUpdate.getType());
        if (materialUpdate.getPrice() != null) existing.setPrice(materialUpdate.getPrice());
        if (materialUpdate.getBuyDate() != null) existing.setBuyDate(materialUpdate.getBuyDate());
        if (materialUpdate.getSoldDate() != null) existing.setSoldDate(materialUpdate.getSoldDate());
        if (materialUpdate.getCityCode() != null) existing.setCityCode(materialUpdate.getCityCode());
        if (materialUpdate.getState() != null) existing.setState(materialUpdate.getState());

        return materialIRepository.save(existing);
    }

}

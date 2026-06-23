package sysman.techassessment.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sysman.techassessment.domain.exception.MaterialAlreadyExists;
import sysman.techassessment.domain.model.Material;
import sysman.techassessment.domain.model.MaterialState;
import sysman.techassessment.domain.port.out.MaterialIRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private MaterialIRepository materialIRepository;

    @InjectMocks
    private MaterialService materialService;

    private Material material;

    @BeforeEach
    void setUp() {
        material = new Material();
        material.setName("Cemento");
        material.setType("Construccion");
        material.setPrice(new BigDecimal("25000"));
        material.setCityCode("BOG");
    }

    @Test
    void testRegisterNewMaterialSuccess() {
        when(materialIRepository.findMaterial("Cemento")).thenReturn(null);
        when(materialIRepository.save(any(Material.class))).thenAnswer(i -> {
            Material savedMaterial = i.getArgument(0);
            savedMaterial.setId(1);
            return savedMaterial;
        });

        Material registeredMaterial = materialService.register(material);

        assertNotNull(registeredMaterial.getId());
        assertNotNull(registeredMaterial.getBuyDate());
        assertEquals(MaterialState.AVAILABLE, registeredMaterial.getState());
        
        verify(materialIRepository, times(1)).findMaterial("Cemento");
        verify(materialIRepository, times(1)).save(any(Material.class));
    }

    @Test
    void testRegisterReactivatesInactiveMaterial() {
        Material inactiveMaterial = new Material();
        inactiveMaterial.setId(1);
        inactiveMaterial.setName("Cemento");
        inactiveMaterial.setState(MaterialState.INACTIVE);

        when(materialIRepository.findMaterial("Cemento")).thenReturn(inactiveMaterial);
        when(materialIRepository.save(any(Material.class))).thenAnswer(i -> i.getArgument(0));

        Material registeredMaterial = materialService.register(material);

        assertEquals(1, registeredMaterial.getId());
        assertEquals(MaterialState.AVAILABLE, registeredMaterial.getState());
        
        verify(materialIRepository, times(1)).findMaterial("Cemento");
        verify(materialIRepository, times(1)).save(any(Material.class));
    }

    @Test
    void testRegisterThrowsExceptionIfMaterialExistsAndIsActive() {
        Material activeMaterial = new Material();
        activeMaterial.setId(1);
        activeMaterial.setName("Cemento");
        activeMaterial.setState(MaterialState.AVAILABLE);

        when(materialIRepository.findMaterial("Cemento")).thenReturn(activeMaterial);

        assertThrows(MaterialAlreadyExists.class, () -> {
            materialService.register(material);
        });

        verify(materialIRepository, times(1)).findMaterial("Cemento");
        verify(materialIRepository, never()).save(any(Material.class));
    }
}

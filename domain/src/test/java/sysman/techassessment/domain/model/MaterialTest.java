package sysman.techassessment.domain.model;

import org.junit.jupiter.api.Test;
import sysman.techassessment.domain.exception.BuyDateOlderThanSoldDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MaterialTest {

    @Test
    void testMaterialCreationSuccess() {
        Material material = new Material(
                1,
                "Ladrillo",
                "Ladrillo hueco",
                "Construccion",
                new BigDecimal("1500"),
                LocalDateTime.of(2023, 1, 1, 10, 0),
                LocalDateTime.of(2023, 1, 10, 10, 0),
                "BOG",
                MaterialState.AVAILABLE
        );

        assertEquals("Ladrillo", material.getName());
        assertEquals(MaterialState.AVAILABLE, material.getState());
    }

    @Test
    void testMaterialCreationFailsWhenBuyDateIsAfterSoldDate() {
        LocalDateTime buyDate = LocalDateTime.of(2023, 1, 10, 10, 0);
        LocalDateTime soldDate = LocalDateTime.of(2023, 1, 1, 10, 0);

        assertThrows(BuyDateOlderThanSoldDate.class, () -> {
            new Material(
                    1,
                    "Ladrillo",
                    "Ladrillo hueco",
                    "Construccion",
                    new BigDecimal("1500"),
                    buyDate,
                    soldDate,
                    "BOG",
                    MaterialState.AVAILABLE
            );
        });
    }

    @Test
    void testSetSoldDateFailsWhenBeforeBuyDate() {
        Material material = new Material();
        material.setBuyDate(LocalDateTime.of(2023, 1, 10, 10, 0));

        assertThrows(BuyDateOlderThanSoldDate.class, () -> {
            material.setSoldDate(LocalDateTime.of(2023, 1, 1, 10, 0));
        });
    }
    
    @Test
    void testSetSoldDateSuccessWhenAfterBuyDate() {
        Material material = new Material();
        material.setBuyDate(LocalDateTime.of(2023, 1, 1, 10, 0));
        
        assertDoesNotThrow(() -> {
            material.setSoldDate(LocalDateTime.of(2023, 1, 10, 10, 0));
        });
        assertNotNull(material.getSoldDate());
    }
}

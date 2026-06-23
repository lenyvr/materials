package sysman.techassessment.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityTest {

    @Test
    void testCityCreation() {
        City city = new City("CUND", "Bogotá", "BOG");
        assertEquals("CUND", city.getDepartmentCode());
        assertEquals("Bogotá", city.getName());
        assertEquals("BOG", city.getCode());
    }

    @Test
    void testCitySetters() {
        City city = new City();
        city.setCode("MDL");
        city.setName("Medellín");
        city.setDepartmentCode("ANT");

        assertEquals("MDL", city.getCode());
        assertEquals("Medellín", city.getName());
        assertEquals("ANT", city.getDepartmentCode());
    }
}

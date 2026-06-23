package sysman.techassessment.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sysman.techassessment.domain.exception.BusinessDomainException;
import sysman.techassessment.domain.model.City;
import sysman.techassessment.domain.port.out.CityIRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityIRepository cityIRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    void testSearchSuccess() {
        City city = new City("CUND", "Bogotá", "BOG");
        when(cityIRepository.getCity("BOG")).thenReturn(city);

        City result = cityService.search("BOG");

        assertNotNull(result);
        assertEquals("BOG", result.getCode());
        assertEquals("Bogotá", result.getName());
        
        verify(cityIRepository, times(2)).getCity("BOG"); // Service calls it twice
    }

    @Test
    void testSearchThrowsExceptionIfCodeIsNull() {
        BusinessDomainException exception = assertThrows(BusinessDomainException.class, () -> {
            cityService.search(null);
        });
        assertEquals("code is null", exception.getMessage());
        verify(cityIRepository, never()).getCity(anyString());
    }

    @Test
    void testSearchThrowsExceptionIfCodeIsEmpty() {
        BusinessDomainException exception = assertThrows(BusinessDomainException.class, () -> {
            cityService.search("   ");
        });
        assertEquals("code is null", exception.getMessage());
        verify(cityIRepository, never()).getCity(anyString());
    }

    @Test
    void testSearchThrowsExceptionIfCityNotFound() {
        when(cityIRepository.getCity("UNK")).thenReturn(null);

        BusinessDomainException exception = assertThrows(BusinessDomainException.class, () -> {
            cityService.search("UNK");
        });
        assertEquals("La ciudad con el código enviado 'UNK' no existe", exception.getMessage());
        verify(cityIRepository, times(1)).getCity("UNK");
    }
}

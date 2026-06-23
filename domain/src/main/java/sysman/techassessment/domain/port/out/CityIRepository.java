package sysman.techassessment.domain.port.out;

import sysman.techassessment.domain.model.City;

public interface CityIRepository {
    City getCity(String code);
}

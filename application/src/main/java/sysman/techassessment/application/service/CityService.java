package sysman.techassessment.application.service;

import sysman.techassessment.application.usecase.CitySPI;
import sysman.techassessment.domain.exception.BusinessDomainException;
import sysman.techassessment.domain.model.City;
import sysman.techassessment.domain.port.out.CityIRepository;

import java.util.Objects;

public class CityService implements CitySPI {

    private final CityIRepository cityIRepository;

    public CityService(CityIRepository cityIRepository) {
        this.cityIRepository = cityIRepository;
    }

    @Override
    public City search(String code) {
        if(Objects.isNull(code) || code.trim().isEmpty()){
            throw new BusinessDomainException("code is null");
        }

        City city = cityIRepository.getCity(code);

        if(Objects.isNull(city)){
            throw new BusinessDomainException(String.format("La ciudad con el código enviado '%s' no existe", code));
        }

        return cityIRepository.getCity(code);
    }

    @Override
    public City searchByName(String name) {
        if(Objects.isNull(name) || name.trim().isEmpty()){
            return null;
        }
        return cityIRepository.getCityByName(name);
    }
}

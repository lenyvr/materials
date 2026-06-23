package sysman.techassessment.application.usecase;

import sysman.techassessment.domain.model.City;

public interface CitySPI {
    City search(String code);
}

package sysman.techassessment.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sysman.techassessment.application.service.CityService;
import sysman.techassessment.application.service.MaterialService;
import sysman.techassessment.application.usecase.CitySPI;
import sysman.techassessment.application.usecase.MaterialSPI;
import sysman.techassessment.domain.port.out.CityIRepository;
import sysman.techassessment.domain.port.out.MaterialIRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public MaterialSPI createMaterialService(MaterialIRepository materialIRepository) {
        return new MaterialService(materialIRepository);
    }

    @Bean
    public CitySPI createCityService(CityIRepository cityIRepository) {
        return new CityService(cityIRepository);
    }
}

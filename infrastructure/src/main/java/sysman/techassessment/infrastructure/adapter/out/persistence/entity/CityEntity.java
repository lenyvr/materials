package sysman.techassessment.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "city")
@Getter
@Setter
public class CityEntity {
    @Id
    private String code;

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @Column(name = "department_code", nullable = false, length = 10)
    private String departmentCode;
}

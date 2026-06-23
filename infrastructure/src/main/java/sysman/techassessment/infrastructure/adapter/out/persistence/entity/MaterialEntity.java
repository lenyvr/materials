package sysman.techassessment.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sysman.techassessment.domain.model.MaterialState;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "material")
@Getter
@Setter
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(nullable = false, length = 59)
    private String type;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "buy_date", nullable = false)
    private LocalDateTime buyDate;

    @Column(name = "sold_date")
    private LocalDateTime soldDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MaterialState state;

    @Column(name = "city_code", nullable = false, length = 10)
    private String cityCode;
}

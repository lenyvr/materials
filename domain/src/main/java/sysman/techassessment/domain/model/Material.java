package sysman.techassessment.domain.model;

import sysman.techassessment.domain.exception.BuyDateOlderThanSoldDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Material {
    private Integer id;
    private String name;
    private String description;
    private String type;
    private BigDecimal price;
    private LocalDateTime buyDate;
    private LocalDateTime soldDate;
    private String cityCode;
    private MaterialState state;

    public Material() {}

    public Material(Integer id, String name, String description, String type, BigDecimal price, LocalDateTime buyDate, LocalDateTime soldDate, String cityCode, MaterialState state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.buyDate = buyDate;
        this.soldDate = soldDate;
        this.cityCode = cityCode;
        this.state = state;
        validateDate();
    }

    private void validateDate(){
        if (Objects.nonNull(this.soldDate) && Objects.nonNull(this.buyDate) && this.buyDate.isAfter(this.soldDate) ) {
            throw new BuyDateOlderThanSoldDate("La fecha de compra no puede ser superior a la fecha de venta");
        }
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getBuyDate() { return buyDate; }
    public void setBuyDate(LocalDateTime buyDate) { this.buyDate = buyDate; }

    public LocalDateTime getSoldDate() { return soldDate; }
    public void setSoldDate(LocalDateTime soldDate) {
        this.soldDate = soldDate;
        validateDate();
    }

    public String getCityCode() { return cityCode; }
    public void setCityCode(String cityCode) { this.cityCode = cityCode; }

    public MaterialState getState() { return state; }
    public void setState(MaterialState state) { this.state = state; }
}

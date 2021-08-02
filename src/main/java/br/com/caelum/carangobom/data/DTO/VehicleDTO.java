package br.com.caelum.carangobom.data.DTO;

import java.math.BigDecimal;
import java.util.Objects;

public class VehicleDTO {
    private Long id;

    private String model;

    private String year;

    private BigDecimal value;

    private BrandDTO brand;

    public VehicleDTO(Long id, String model, String year, BigDecimal value, BrandDTO brand) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.value = value;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BrandDTO getBrand() {
        return brand;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        VehicleDTO vehicleDTO = (VehicleDTO) obj;
        return Objects.equals(id, vehicleDTO.id)
                && model.equals(vehicleDTO.model)
                && year.equals(vehicleDTO.year)
                && value.equals(vehicleDTO.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

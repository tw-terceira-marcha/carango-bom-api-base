package br.com.caelum.carangobom.data.DTO;

import java.math.BigDecimal;
import java.util.Objects;

public class VehicleDTO {
    private Long id;

    private String model;

    private int year;

    private BigDecimal value;

    private BrandDTO brand;

    public VehicleDTO(Long id, String model, int year, BigDecimal value, BrandDTO brand) {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDTO that = (VehicleDTO) o;
        return year == that.year && Objects.equals(model, that.model) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, year, value);
    }
}

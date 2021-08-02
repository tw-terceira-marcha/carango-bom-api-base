package br.com.caelum.carangobom.data.form;

import java.math.BigDecimal;

public class VehicleForm {
    
    private String model;

    private String year;

    private BigDecimal value;

    private Long brandId;

    public VehicleForm(String model, String year, BigDecimal value) {
        this.model = model;
        this.year = year;
        this.value = value;
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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

}

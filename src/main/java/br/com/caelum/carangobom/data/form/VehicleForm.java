package br.com.caelum.carangobom.data.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class VehicleForm {
    @NotBlank
    @NotNull
    @Size(min = 2, message = "Must have {min} or more characters.")
    private String model;

    @NotNull
    private int year;

    @NotNull
    private BigDecimal value;

    @NotNull
    private Long brandId;

    public VehicleForm(String model, int year, BigDecimal value, Long brandId) {
        this.model = model;
        this.year = year;
        this.value = value;
        this.brandId = brandId;
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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

}

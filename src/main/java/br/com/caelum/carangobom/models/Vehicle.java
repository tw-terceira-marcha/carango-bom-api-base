package br.com.caelum.carangobom.models;

import java.math.BigDecimal;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private int year;

    private BigDecimal value;

    @ManyToOne
    private Brand brand;

    public Vehicle() {

    }

    public Vehicle(Long id, String model, int year, BigDecimal value, Brand brand) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.value = value;
        this.brand = brand;
    }

    public Vehicle(String model, int year, BigDecimal value, Brand brand) {
        this(null, model, year, value, brand);
    }

    public Long getId() {
        return id;
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vehicle other = (Vehicle) obj;
        return Objects.equals(model, other.model) 
            && Objects.equals(brand.getName(), other.brand.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, brand.getName());
    }

}

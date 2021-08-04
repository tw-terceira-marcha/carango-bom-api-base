package br.com.caelum.carangobom.models.custom;

import java.math.BigDecimal;

public interface IVehicleSummary {
    Integer getBrandId();
    String getBrandName();
    Integer getCount();
    BigDecimal getTotal();
}

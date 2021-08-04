package br.com.caelum.carangobom.repository.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.caelum.carangobom.models.Vehicle;
import br.com.caelum.carangobom.models.custom.IVehicleSummary;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query(nativeQuery = true, value = "SELECT v.brand_id AS brandId, b.name AS brandName, COUNT(1) AS count, SUM(v.value) AS total "
        + "FROM vehicle AS v "
        + "JOIN brand b ON b.id = v.brand_id "
        + "GROUP BY v.brand_id")
    List<IVehicleSummary> groupByBrand();
}

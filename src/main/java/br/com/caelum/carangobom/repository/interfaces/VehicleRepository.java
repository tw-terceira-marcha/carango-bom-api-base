package br.com.caelum.carangobom.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caelum.carangobom.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}

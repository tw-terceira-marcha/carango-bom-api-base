package br.com.caelum.carangobom.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import br.com.caelum.carangobom.data.DTO.VehicleDTO;
import br.com.caelum.carangobom.data.form.VehicleForm;
import br.com.caelum.carangobom.models.Brand;
import br.com.caelum.carangobom.models.Vehicle;
import br.com.caelum.carangobom.repository.interfaces.BrandRepository;
import br.com.caelum.carangobom.repository.interfaces.VehicleRepository;
import br.com.caelum.carangobom.service.impl.BrandService;
import br.com.caelum.carangobom.service.impl.VehicleService;
import br.com.caelum.carangobom.service.interfaces.IBrandService;
import br.com.caelum.carangobom.service.interfaces.IVehicleService;

class VehicleServiceTest {

    private IVehicleService vehicleService;
    private IBrandService brandService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private BrandRepository brandRepository;

    @BeforeEach
    public void setupMocks() {
        openMocks(this);

        this.brandService = new BrandService(brandRepository);
        this.vehicleService = new VehicleService(vehicleRepository, brandService);
    }

    @Test
    void entityToDTO() {
        var brand = new Brand("VW");
        var vehicle = new Vehicle("Polo", "2019", new BigDecimal("50000"), brand);
        
        VehicleDTO dto = this.vehicleService.entityToDTO(vehicle);

        assertEquals(dto.getModel(), vehicle.getModel());
        assertEquals(dto.getYear(), vehicle.getYear());
        assertEquals(dto.getValue(), vehicle.getValue());
    }

    @Test
    void formToEntity() {
        var form = new VehicleForm("Polo", "2019", new BigDecimal("50000"));

        var vehicle = vehicleService.formToEntity(form);

        assertEquals(vehicle.getId(), null);
        assertEquals(vehicle.getModel(), form.getModel());
        assertEquals(vehicle.getYear(), form.getYear());
        assertEquals(vehicle.getValue(), form.getValue());
        assertEquals(vehicle.getBrand().getId(), form.getBrandId());
    }

    @Test
    void updateEntity() {
        var brand = new Brand("VW");
        var vehicle = new Vehicle(1L, "Polo", "2019", new BigDecimal("50000"), brand);
        var form = new VehicleForm("Gol", "2018", new BigDecimal("40000"));

        vehicleService.updateEntity(vehicle, form);

        assertEquals(vehicle.getModel(), form.getModel());
        assertEquals(vehicle.getYear(), form.getYear());
        assertEquals(vehicle.getValue(), form.getValue());
    }

    @Test
    void getVehicles() {
        var brand = new Brand("VW");
        List<Vehicle> vehicles = List.of(
                new Vehicle(1L, "Polo", "2019", new BigDecimal("50000"), brand),
                new Vehicle(1L, "Gol", "2018", new BigDecimal("40000"), brand),
                new Vehicle(1L, "Virtus", "2020", new BigDecimal("70000"), brand));

        when(vehicleRepository.findAll())
            .thenReturn(vehicles);

        var list = vehicleService.getList();
        assertEquals(
            vehicles
                .stream()
                .map(vehicleService::entityToDTO)
                .collect(Collectors.toList()),
            list);
    }

}

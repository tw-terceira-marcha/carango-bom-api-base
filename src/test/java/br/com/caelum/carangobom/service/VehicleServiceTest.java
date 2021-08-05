package br.com.caelum.carangobom.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

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
        this.vehicleService = new VehicleService(vehicleRepository, brandService, brandRepository);
    }

    @Test
    void entityToDTO() {
        Brand brand = new Brand("VW");
        Vehicle vehicle = new Vehicle("Polo", 2019, new BigDecimal("50000"), brand);

        VehicleDTO dto = this.vehicleService.entityToDTO(vehicle);

        assertEquals(dto.getModel(), vehicle.getModel());
        assertEquals(dto.getYear(), vehicle.getYear());
        assertEquals(dto.getValue(), vehicle.getValue());
    }

    @Test
    void formToEntity() {
        VehicleForm form = new VehicleForm("Polo", 2019, new BigDecimal("50000"), 1L);

        Vehicle vehicle = vehicleService.formToEntity(form);

        assertNull(vehicle.getId());
        assertEquals(vehicle.getModel(), form.getModel());
        assertEquals(vehicle.getYear(), form.getYear());
        assertEquals(vehicle.getValue(), form.getValue());
        assertEquals(vehicle.getBrand().getId(), form.getBrandId());
    }

    @Test
    void updateEntity() {
        Brand brand = new Brand("VW");
        Vehicle vehicle = new Vehicle(1L, "Polo", 2019, new BigDecimal("50000"), brand);
        VehicleForm form = new VehicleForm("Gol", 2018, new BigDecimal("40000"), 1L);

        vehicleService.updateEntity(vehicle, form);

        assertEquals(vehicle.getModel(), form.getModel());
        assertEquals(vehicle.getYear(), form.getYear());
        assertEquals(vehicle.getValue(), form.getValue());
    }

    @Test
    void getVehicles() {
        Brand brand = new Brand("VW");
        List<Vehicle> vehicles = List.of(
                new Vehicle(1L, "Polo", 2019, new BigDecimal("50000"), brand),
                new Vehicle(1L, "Gol", 2018, new BigDecimal("40000"), brand),
                new Vehicle(1L, "Virtus", 2020, new BigDecimal("70000"), brand));

        when(vehicleRepository.findAll())
            .thenReturn(vehicles);

        List<VehicleDTO> list = vehicleService.getList();
        assertEquals(
            vehicles
                .stream()
                .map(vehicleService::entityToDTO)
                .collect(Collectors.toList()),
            list);
    }

    @Test
    void createEntity() {
        Long brandId = 1L;
        Brand brand = new Brand(brandId, "VW");
        VehicleForm form = new VehicleForm("Gol", 2018, new BigDecimal("40000"), brandId);
        Vehicle vehicle = new Vehicle("Gol", 2018, new BigDecimal("40000"), brand);

        when(brandRepository.findById(brandId))
                .thenReturn(Optional.of(brand));

        when(vehicleRepository.save(vehicle))
                .then(invocation -> invocation.getArgument(0, Vehicle.class));

        VehicleDTO vehicleDTO = vehicleService.create(form).get();

        assertEquals(vehicleDTO.getModel(), form.getModel());
        assertEquals(vehicleDTO.getYear(), form.getYear());
        assertEquals(vehicleDTO.getValue(), form.getValue());
        assertEquals(vehicleDTO.getBrand().getId(), form.getBrandId());
    }
}

package br.com.caelum.carangobom.service.impl;

import br.com.caelum.carangobom.data.DTO.BrandDTO;
import br.com.caelum.carangobom.data.DTO.VehicleDTO;
import br.com.caelum.carangobom.data.form.VehicleForm;
import br.com.caelum.carangobom.models.Brand;
import br.com.caelum.carangobom.models.Vehicle;
import br.com.caelum.carangobom.models.custom.IVehicleSummary;
import br.com.caelum.carangobom.repository.interfaces.BrandRepository;
import br.com.caelum.carangobom.repository.interfaces.VehicleRepository;
import br.com.caelum.carangobom.service.interfaces.IBrandService;
import br.com.caelum.carangobom.service.interfaces.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService implements IVehicleService {

    private VehicleRepository repository;

    private IBrandService brandService;

    private BrandRepository brandRepository;

    @Autowired
    public VehicleService(VehicleRepository repository, IBrandService brandService, BrandRepository brandRepository) {
        this.repository = repository;
        this.brandService = brandService;
        this.brandRepository = brandRepository;
    }

    @Override
    public VehicleDTO entityToDTO(Vehicle entity) {
        BrandDTO brandDTO = brandService.entityToDTO(entity.getBrand());

        return new VehicleDTO(entity.getId(), entity.getModel(), entity.getYear(), entity.getValue(), brandDTO);
    }

    @Override
    public Vehicle formToEntity(VehicleForm form) {
        Brand brand = new Brand();
        brand.setId(form.getBrandId());

        return new Vehicle(form.getModel(), form.getYear(), form.getValue(), brand);
    }

    @Override
    public void updateEntity(Vehicle entity, VehicleForm form) {
        entity.setModel(form.getModel());
        entity.setYear(form.getYear());
        entity.setValue(form.getValue());

        loadBrandById(form, entity);
    }

    @Override
    public VehicleDTO create(VehicleForm form) {
        Vehicle vehicle = this.formToEntity(form);

        loadBrandById(form, vehicle);

        vehicle = this.getRepository().save(vehicle);

        return this.entityToDTO(vehicle);
    }

    @Override
    public VehicleRepository getRepository() {
        return this.repository;
    }

    @Override
    public List<VehicleDTO> getList() {
        return this.repository
                .findAll()
                .stream()
                .map(this::entityToDTO).collect(Collectors.toList());
    }

    public List<IVehicleSummary> getSummaryByBrand() {
        return this.repository.groupByBrand();
    }

    private void loadBrandById(VehicleForm form, Vehicle vehicle) {
        if (form.getBrandId() == null) {
            return;
        }
        this.brandRepository
                .findById(form.getBrandId())
                .ifPresent(vehicle::setBrand);
    }
}
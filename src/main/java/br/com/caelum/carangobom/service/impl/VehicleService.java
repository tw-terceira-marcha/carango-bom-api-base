package br.com.caelum.carangobom.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.data.DTO.BrandDTO;
import br.com.caelum.carangobom.data.DTO.VehicleDTO;
import br.com.caelum.carangobom.data.form.VehicleForm;
import br.com.caelum.carangobom.models.Brand;
import br.com.caelum.carangobom.models.Vehicle;
import br.com.caelum.carangobom.repository.interfaces.VehicleRepository;
import br.com.caelum.carangobom.service.interfaces.IBrandService;
import br.com.caelum.carangobom.service.interfaces.IVehicleService;

@Service
public class VehicleService implements IVehicleService {

    private VehicleRepository repository;

    private IBrandService brandService;

    @Autowired
    public VehicleService(VehicleRepository repository, IBrandService brandService) {
        this.repository = repository;
        this.brandService = brandService;
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
        return this.repository.findAll().stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    private void loadBrandById(VehicleForm form, Vehicle vehicle) {
        if (form.getBrandId() == null) {
            return;
        }

        var optional = this.brandService.getById(form.getBrandId());
        if (optional.isPresent()) {
            var brandDTO = optional.get();
            vehicle.setBrand(new Brand(form.getBrandId(), brandDTO.getName()));
        }
    }
}
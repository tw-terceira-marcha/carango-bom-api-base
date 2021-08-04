package br.com.caelum.carangobom.controllers.impl;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.controllers.interfaces.IBaseController;
import br.com.caelum.carangobom.controllers.interfaces.IFormValidation;
import br.com.caelum.carangobom.data.DTO.VehicleDTO;
import br.com.caelum.carangobom.data.form.VehicleForm;
import br.com.caelum.carangobom.models.Vehicle;
import br.com.caelum.carangobom.models.custom.IVehicleSummary;
import br.com.caelum.carangobom.repository.interfaces.VehicleRepository;
import br.com.caelum.carangobom.service.interfaces.IVehicleService;

@RequestMapping("/vehicles")
@RestController
public class VehicleController
    implements IBaseController<Long, Vehicle, VehicleRepository, VehicleDTO, VehicleForm, VehicleForm, IVehicleService>,
    IFormValidation {

    @Autowired
    public IVehicleService vehicleService;

    @GetMapping("/summary")
    public List<IVehicleSummary> summary() {
        return this.vehicleService.getSummaryByBrand();
    }

    @Override
    public IVehicleService getService() {
        return this.vehicleService;
    }

    @Override
    public URI getCreatedURI(VehicleDTO dto) {
        return UriComponentsBuilder
            .newInstance()
            .path("/brands/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
    }
}

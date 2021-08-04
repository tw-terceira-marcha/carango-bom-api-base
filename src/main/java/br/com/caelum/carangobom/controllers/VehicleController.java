package br.com.caelum.carangobom.controllers;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.data.DTO.VehicleDTO;
import br.com.caelum.carangobom.data.form.VehicleForm;
import br.com.caelum.carangobom.models.custom.IVehicleSummary;
import br.com.caelum.carangobom.service.interfaces.IVehicleService;

@RequestMapping("/vehicles")
@RestController
public class VehicleController implements IFormValidation {

    @Autowired
    public IVehicleService vehicleService;

    @GetMapping()
    public List<VehicleDTO> list() {
        return this.vehicleService.getList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> details(@PathVariable Long id) {
        return this.vehicleService
                .getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/summary")
    public List<IVehicleSummary> summary() {
        return this.vehicleService.getSummaryByBrand();
    }

    @PostMapping()
    public ResponseEntity<VehicleDTO> create(
            @Valid @RequestBody VehicleForm form,
            UriComponentsBuilder uriBuilder) {
        VehicleDTO vehicle = this.vehicleService.create(form);
        URI uri = uriBuilder.path("/vehicles/{id}").buildAndExpand(vehicle.getId()).toUri();
        return ResponseEntity.created(uri).body(vehicle);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<VehicleDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody VehicleForm vehicleForm) {
        return this.vehicleService
                .update(id, vehicleForm)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VehicleDTO> delete(@PathVariable Long id) {
        return this.vehicleService
                .delete(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

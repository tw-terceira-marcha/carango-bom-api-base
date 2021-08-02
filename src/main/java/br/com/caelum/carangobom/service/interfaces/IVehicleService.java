package br.com.caelum.carangobom.service.interfaces;

import java.util.List;

import br.com.caelum.carangobom.data.DTO.VehicleDTO;
import br.com.caelum.carangobom.data.form.VehicleForm;
import br.com.caelum.carangobom.models.Vehicle;
import br.com.caelum.carangobom.repository.interfaces.VehicleRepository;

public interface IVehicleService 
    extends IBaseService<Vehicle, Long, VehicleRepository, VehicleDTO, VehicleForm, VehicleForm> {

        public List<VehicleDTO> getList();
}

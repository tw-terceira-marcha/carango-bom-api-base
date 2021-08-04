package br.com.caelum.carangobom.service.interfaces;

import br.com.caelum.carangobom.data.DTO.BrandDTO;
import br.com.caelum.carangobom.data.form.BrandForm;
import br.com.caelum.carangobom.models.Brand;
import br.com.caelum.carangobom.repository.interfaces.BrandRepository;

public interface IBrandService
    extends IBaseService<Brand, Long, BrandRepository, BrandDTO, BrandForm, BrandForm> {

}

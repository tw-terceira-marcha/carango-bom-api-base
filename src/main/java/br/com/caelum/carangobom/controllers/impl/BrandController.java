package br.com.caelum.carangobom.controllers.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.controllers.interfaces.IBaseController;
import br.com.caelum.carangobom.controllers.interfaces.IFormValidation;
import br.com.caelum.carangobom.data.DTO.BrandDTO;
import br.com.caelum.carangobom.data.form.BrandForm;
import br.com.caelum.carangobom.models.Brand;
import br.com.caelum.carangobom.repository.interfaces.BrandRepository;
import br.com.caelum.carangobom.service.interfaces.IBrandService;

@RequestMapping("/brands")
@RestController
public class BrandController
    implements IBaseController<Long, Brand, BrandRepository, BrandDTO, BrandForm, BrandForm, IBrandService>,
    IFormValidation {

    @Autowired
    public IBrandService brandService;

    @Override
    public IBrandService getService() {
        return this.brandService;
    }

    @Override
    public URI getCreatedURI(BrandDTO dto) {
        return UriComponentsBuilder
            .newInstance()
            .path("/brands/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
    }

}

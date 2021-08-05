package br.com.caelum.carangobom.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.data.DTO.BrandDTO;
import br.com.caelum.carangobom.data.form.BrandForm;
import br.com.caelum.carangobom.models.Brand;
import br.com.caelum.carangobom.repository.interfaces.BrandRepository;
import br.com.caelum.carangobom.service.interfaces.IBrandService;

@Service
public class BrandService implements IBrandService {

    private BrandRepository repository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.repository = brandRepository;
    }

    @Override
    public List<BrandDTO> getList() {
        return this.repository
                .findAllByOrderByName()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDTO entityToDTO(Brand brand){
        return new BrandDTO(brand.getId(), brand.getName());
    }

    @Override
    public Brand formToEntity(BrandForm brandForm){
        return new Brand(brandForm.getName());
    }

    @Override
    public void updateEntity(Brand brand, BrandForm form) {
        brand.setName(form.getName());
    }

    @Override
    public BrandRepository getRepository() {
        return this.repository;
    }

    @Override
    public boolean entityExists(BrandForm form) {
        return this.repository.existsByName(form.getName());
    }
}

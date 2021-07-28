package br.com.caelum.carangobom.service.impl;

import br.com.caelum.carangobom.data.DTO.BrandDTO;
import br.com.caelum.carangobom.data.form.BrandForm;
import br.com.caelum.carangobom.models.Brand;
import br.com.caelum.carangobom.repository.interfaces.BrandRepository;
import br.com.caelum.carangobom.service.interfaces.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService implements IBrandService {

    private BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDTO> getList() {
        return this.brandRepository
                .findAllByOrderByName()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BrandDTO> getById(Long id) {
        return this.brandRepository
                .findById(id)
                .map(this::entityToDTO);
    }

    @Override
    public BrandDTO create(BrandForm brand) {
        return this.entityToDTO(this.brandRepository.save(this.formToEntity(brand)));
    }

    @Override
    @Transactional
    public Optional<BrandDTO> update(Long id, BrandForm brandForm) {
        return this.brandRepository
                .findById(id)
                .map(brand -> {
                    brand.setName(brandForm.getName());
                    return this.entityToDTO(brand);
                });
    }

    @Override
    @Transactional
    public Optional<BrandDTO> delete(Long id) {
        return this.brandRepository
                .findById(id)
                .map(brand -> {
                    this.brandRepository.delete(brand);
                    return this.entityToDTO(brand);
                });
    }

    private BrandDTO entityToDTO(Brand brand){
        return new BrandDTO(brand.getId(), brand.getName());
    }

    private Brand formToEntity(BrandForm brandForm){
        return new Brand(brandForm.getName());
    }
}

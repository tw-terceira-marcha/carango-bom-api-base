package br.com.caelum.carangobom.service.interfaces;

import br.com.caelum.carangobom.data.DTO.BrandDTO;
import br.com.caelum.carangobom.data.form.BrandForm;

import java.util.List;
import java.util.Optional;

public interface IBrandService {
    public List<BrandDTO> getList();

    public Optional<BrandDTO> getById(Long id);

    public BrandDTO create(BrandForm brand);

    public Optional<BrandDTO> update(Long id, BrandForm brand);

    public Optional<BrandDTO> delete(Long id);

}

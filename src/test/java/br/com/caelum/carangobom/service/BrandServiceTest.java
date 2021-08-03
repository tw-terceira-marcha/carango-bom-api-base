package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.data.DTO.BrandDTO;
import br.com.caelum.carangobom.data.form.BrandForm;
import br.com.caelum.carangobom.models.Brand;
import br.com.caelum.carangobom.repository.interfaces.BrandRepository;
import br.com.caelum.carangobom.service.impl.BrandService;
import br.com.caelum.carangobom.service.interfaces.IBrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class BrandServiceTest {

    private IBrandService service;

    @Mock
    private BrandRepository brandRepository;

    @BeforeEach
    public void setupMocks() {
        openMocks(this);

        this.service = new BrandService(brandRepository);
    }

    @Test
    void getBrands() {
        List<Brand> brands = List.of(
                new Brand(1L, "Audi"),
                new Brand(2L, "BMW"),
                new Brand(3L, "Fiat"));

        when(brandRepository.findAllByOrderByName())
                .thenReturn(brands);

        List<BrandDTO> list = service.getList();
        assertEquals(
                brands
                        .stream()
                        .map(service::entityToDTO)
                        .collect(Collectors.toList()),
                list);
    }

    @Test
    void entityToDTO() {
        Brand brand = new Brand("Gurgel");

        BrandDTO dto = service.entityToDTO(brand);

        assertEquals(dto.getName(), brand.getName());
    }


    @Test
    void formToEntity() {
        BrandForm form = new BrandForm("Gurgel");

        Brand brand = service.formToEntity(form);

        assertNull(brand.getId());
        assertEquals(brand.getName(), form.getName());
    }

    @Test
    void updateEntity() {
        Long id = 1L;
        Brand brand = new Brand(id, "Fiat");
        BrandForm form = new BrandForm("Gurgel");

        service.updateEntity(brand, form);

        assertEquals(brand.getId(), id);
        assertEquals(brand.getName(), form.getName());
    }
}

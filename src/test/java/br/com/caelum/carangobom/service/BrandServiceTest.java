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
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class BrandServiceTest {

    private IBrandService brandService;

    @Mock
    private BrandRepository brandRepository;

    @BeforeEach
    public void setupMocks() {
        openMocks(this);

        this.brandService = new BrandService(brandRepository);
    }

    @Test
    void getBrands() {
        List<Brand> brands = List.of(
            new Brand(1L, "Audi"),
            new Brand(2L, "BMW"),
            new Brand(3L, "Fiat")
        );

        when(brandRepository.findAllByOrderByName())
            .thenReturn(brands);

        List<BrandDTO> list = brandService.getList();
        assertEquals(
                brands
                    .stream()
                    .map(this::entityToDTO)
                    .collect(Collectors.toList())
                , list);
    }

    @Test
    void getBrand() {
        var audi = new Brand(1L, "Audi");
        when(brandRepository.findById(1L))
            .thenReturn(Optional.of(audi));
        Optional<BrandDTO> brandDTO = brandService.getById(1L);
        assertEquals(brandDTO.get(), entityToDTO(audi));
    }

    @Test
    void getInexistentBrand() {
        when(brandRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Optional<BrandDTO> brandDTO = brandService.getById(1L);
        assertFalse(brandDTO.isPresent());
    }

    @Test
    void createBrand() {
        BrandForm brandForm = new BrandForm("Ferrari");

        when(brandRepository.save(formToEntity(brandForm)))
            .then(invocation -> {
                Brand savedBrand = invocation.getArgument(0, Brand.class);
                savedBrand.setId(1L);

                return savedBrand;
            });

        BrandDTO brandDTO = brandService.create(brandForm);
        assertEquals(brandForm.getName(), brandDTO.getName());
    }

    @Test
    void updateBrand() {
        Brand audi = new Brand(1L, "Audi");
        BrandForm newBrandName = new BrandForm("Fiat");

        when(brandRepository.findById(1L))
            .thenReturn(Optional.of(audi));

        Optional<BrandDTO> brandDTO = brandService.update(1L, newBrandName);
        assertEquals(audi.getName(), newBrandName.getName());
        assertEquals(brandDTO.get().getName(), newBrandName.getName());
    }

    @Test
    void updateInexistentBrand() {
        when(brandRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Optional<BrandDTO> brandDTO = brandService.update(1L, new BrandForm("Fiat"));
        assertFalse(brandDTO.isPresent());
    }

    @Test
    void deleteBrand() {
        Brand audi = new Brand(1l, "Audi");

        when(brandRepository.findById(audi.getId()))
            .thenReturn(Optional.of(audi));

        Optional<BrandDTO> brandDTO = brandService.delete(audi.getId());
        assertTrue(brandDTO.isPresent());
        verify(brandRepository).delete(audi);
    }

    @Test
    void deleteInexistentBrand() {
        when(brandRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Optional<BrandDTO> brandDTO = brandService.delete(1L);
        assertFalse(brandDTO.isPresent());

        verify(brandRepository, never()).delete(any());
    }

    private BrandDTO entityToDTO(Brand brand){
        return new BrandDTO(brand.getId(), brand.getName());
    }

    private Brand formToEntity(BrandForm brandForm){
        return new Brand(brandForm.getName());
    }

}

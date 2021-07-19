package br.com.caelum.carangobom.marca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class BrandControllerTest {

    private BrandController brandController;
    private UriComponentsBuilder uriBuilder;

    @Mock
    private BrandRepository brandRepository;

    @BeforeEach
    public void setupMocks() {
        openMocks(this);

        brandController = new BrandController(brandRepository);
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
    }

    @Test
    void getBrands() {
        List<Brand> brands = List.of(
            new Brand(1L, "Audi"),
            new Brand(2L, "BMW"),
            new Brand(3L, "Fiat")
        );

        when(brandRepository.findAllByOrderByNome())
            .thenReturn(brands);

        List<Brand> response = brandController.list();
        assertEquals(brands, response);
    }

    @Test
    void getBrand() {
        Brand audi = new Brand(1L, "Audi");

        when(brandRepository.findById(1L))
            .thenReturn(Optional.of(audi));

        ResponseEntity<Brand> response = brandController.id(1L);
        assertEquals(audi, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getInexistentBrand() {
        when(brandRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<Brand> response = brandController.id(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createBrandReturnsResourceLocation() {
        Brand brand = new Brand("Ferrari");

        when(brandRepository.save(brand))
            .then(invocation -> {
                Brand savedBrand = invocation.getArgument(0, Brand.class);
                savedBrand.setId(1L);

                return savedBrand;
            });

        ResponseEntity<Brand> response = brandController.create(brand, uriBuilder);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("http://localhost:8080/brands/1", response.getHeaders().getLocation().toString());
    }

    @Test
    void updateBrand() {
        Brand audi = new Brand(1L, "Audi");

        when(brandRepository.findById(1L))
            .thenReturn(Optional.of(audi));

        ResponseEntity<Brand> response = brandController.update(1L, new Brand(1L, "Fiat"));
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Brand brand = response.getBody();
        assertEquals("Fiat", brand.getName());
    }

    @Test
    void updateInexistentBrand() {
        when(brandRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<Brand> response = brandController.update(1L, new Brand(1L, "Fiat"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteBrand() {
        Brand audi = new Brand(1l, "Audi");

        when(brandRepository.findById(1L))
            .thenReturn(Optional.of(audi));

        ResponseEntity<Brand> response = brandController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(brandRepository).delete(audi);
    }

    @Test
    void deleteInexistentBrand() {
        when(brandRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<Brand> response = brandController.delete(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(brandRepository, never()).delete(any());
    }

}

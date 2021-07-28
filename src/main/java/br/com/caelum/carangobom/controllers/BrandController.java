package br.com.caelum.carangobom.controllers;

import br.com.caelum.carangobom.data.DTO.BrandDTO;
import br.com.caelum.carangobom.data.DTO.FieldErrorDTO;
import br.com.caelum.carangobom.data.form.BrandForm;
import br.com.caelum.carangobom.service.interfaces.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BrandController {

    public IBrandService brandService;

    @Autowired
    public BrandController(IBrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands")
    public List<BrandDTO> list() {
        return this.brandService.getList();
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> id(@PathVariable Long id) {
        Optional<BrandDTO> brand = this.brandService.getById(id);
        if (brand.isPresent()) {
            return ResponseEntity.ok(brand.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/brands")
    public ResponseEntity<BrandDTO> create(@Valid @RequestBody BrandForm brandForm, UriComponentsBuilder uriBuilder) {
        BrandDTO brand = this.brandService.create(brandForm);
        URI uri = uriBuilder.path("/brands/{id}").buildAndExpand(brand.getId()).toUri();
        return ResponseEntity.created(uri).body(brand);
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> update(@PathVariable Long id, @Valid @RequestBody BrandForm brandForm) {
        Optional<BrandDTO> brandOptional = this.brandService.update(id, brandForm);
        if (brandOptional.isPresent()) {
            return ResponseEntity.ok(brandOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> delete(@PathVariable Long id) {
        Optional<BrandDTO> brandOptional = this.brandService.delete(id);
        if (brandOptional.isPresent()) {
            return ResponseEntity.ok(brandOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FieldErrorDTO> validate(MethodArgumentNotValidException exception) {
        return exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ex -> {
                    FieldErrorDTO error = new FieldErrorDTO();
                    error.setField(ex.getField());
                    error.setMessage(ex.getDefaultMessage());
                    return error;
                })
                .collect(Collectors.toList());
    }
}

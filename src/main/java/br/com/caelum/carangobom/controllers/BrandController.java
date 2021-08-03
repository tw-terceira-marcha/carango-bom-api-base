package br.com.caelum.carangobom.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.data.DTO.BrandDTO;
import br.com.caelum.carangobom.data.DTO.FieldErrorDTO;
import br.com.caelum.carangobom.data.form.BrandForm;
import br.com.caelum.carangobom.service.interfaces.IBrandService;

@RestController
public class BrandController {

    @Autowired
    public IBrandService brandService;

    @GetMapping("/brands")
    public List<BrandDTO> list() {
        return this.brandService.getList();
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> id(@PathVariable Long id) {
        return this.brandService
                .getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/brands")
    public ResponseEntity<BrandDTO> create(
            @Valid @RequestBody BrandForm brandForm,
            UriComponentsBuilder uriBuilder) {
        BrandDTO brand = this.brandService.create(brandForm);
        URI uri = uriBuilder.path("/brands/{id}").buildAndExpand(brand.getId()).toUri();
        return ResponseEntity.created(uri).body(brand);
    }

    @PutMapping("/brands/{id}")
    @Transactional
    public ResponseEntity<BrandDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody BrandForm brandForm) {
        return this.brandService
                .update(id, brandForm)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> delete(@PathVariable Long id) {
        return this.brandService
                .delete(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FieldErrorDTO> validate(MethodArgumentNotValidException exception) {
        return exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ex -> new FieldErrorDTO(ex.getField(), ex.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}

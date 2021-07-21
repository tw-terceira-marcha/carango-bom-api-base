package br.com.caelum.carangobom.brand;

import br.com.caelum.carangobom.validation.FieldErrorDTO;
import br.com.caelum.carangobom.validation.FieldErrorsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BrandController {

    private BrandRepository mr;

    @Autowired
    public BrandController(BrandRepository mr) {
        this.mr = mr;
    }

    @GetMapping("/brands")
    @Transactional
    public List<Brand> list() {
        return mr.findAllByOrderByName();
    }

    @GetMapping("/brands/{id}")
    @Transactional
    public ResponseEntity<Brand> id(@PathVariable Long id) {
        Optional<Brand> m1 = mr.findById(id);
        if (m1.isPresent()) {
            return ResponseEntity.ok(m1.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/brands")
    @Transactional
    public ResponseEntity<Brand> create(@Valid @RequestBody Brand m1, UriComponentsBuilder uriBuilder) {
        Brand m2 = mr.save(m1);
        URI h = uriBuilder.path("/brands/{id}").buildAndExpand(m1.getId()).toUri();
        return ResponseEntity.created(h).body(m2);
    }

    @PutMapping("/brands/{id}")
    @Transactional
    public ResponseEntity<Brand> update(@PathVariable Long id, @Valid @RequestBody Brand m1) {
        Optional<Brand> m2 = mr.findById(id);
        if (m2.isPresent()) {
            Brand m3 = m2.get();
            m3.setName(m1.getName());
            return ResponseEntity.ok(m3);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/brands/{id}")
    @Transactional
    public ResponseEntity<Brand> delete(@PathVariable Long id) {
        Optional<Brand> m1 = mr.findById(id);
        if (m1.isPresent()) {
            Brand m2 = m1.get();
            mr.delete(m2);
            return ResponseEntity.ok(m2);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public FieldErrorsDTO validate(MethodArgumentNotValidException exception) {
        List<FieldErrorDTO> l = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(e -> {
            FieldErrorDTO d = new FieldErrorDTO();
            d.setField(e.getField());
            d.setMessage(e.getDefaultMessage());
            l.add(d);
        });
        FieldErrorsDTO l2 = new FieldErrorsDTO();
        l2.setErrors(l);
        return l2;
    }
}

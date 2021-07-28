package br.com.caelum.carangobom.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caelum.carangobom.models.Brand;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    public List<Brand> findAllByOrderByName();
}

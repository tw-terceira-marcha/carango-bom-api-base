package br.com.caelum.carangobom.repository.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caelum.carangobom.models.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    public List<Brand> findAllByOrderByName();

    public boolean existsByName(String name);

}

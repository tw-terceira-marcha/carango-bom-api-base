package br.com.caelum.carangobom.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    public List<Brand> findAllByOrderByName();
}
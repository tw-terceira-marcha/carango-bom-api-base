package br.com.caelum.carangobom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caelum.carangobom.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
}

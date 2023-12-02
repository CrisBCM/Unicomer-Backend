package com.project.unicomer.repository;

import com.project.unicomer.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByDni(String dni);
    public boolean existsByDni(String dni);

}

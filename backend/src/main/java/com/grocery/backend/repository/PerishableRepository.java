package com.grocery.backend.repository;

import com.grocery.backend.entity.Perishables;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerishableRepository extends JpaRepository<Perishables, String> {
}

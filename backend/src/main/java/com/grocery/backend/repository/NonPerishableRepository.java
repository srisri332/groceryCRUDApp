package com.grocery.backend.repository;

import com.grocery.backend.entity.NonPerishables;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonPerishableRepository extends JpaRepository<NonPerishables, String> {
}

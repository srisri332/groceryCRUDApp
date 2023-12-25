package com.grocery.backend.repository;

import com.grocery.backend.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {
}

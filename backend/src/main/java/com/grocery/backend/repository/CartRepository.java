package com.grocery.backend.repository;

import com.grocery.backend.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<CartItem, String> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE CartItem SET quantity= :quantity WHERE (productID = :productid)")
    public int updateCartValues(@Param("productid") String productid, @Param("quantity") int quantity);

    @Transactional
    public void deleteByProductID(String productID);
}

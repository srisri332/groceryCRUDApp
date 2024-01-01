package com.grocery.backend.repository;

import com.grocery.backend.entity.OrderDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {
    @Transactional
    @Query(value = "SELECT orderDetailsID, orderID, productAmount, productID, quantity, amountOwed FROM OrderDetails where orderID=:orderID and productID=:productID")
    public String findProductInOrder(@Param("orderID") String orderID, @Param("productID") String productID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE OrderDetails SET quantity= :quantity, productAmount= :productAmount, amountOwed=:amountOwed  WHERE (orderID=:orderID and productID=:productID)")
    public int updateProductDetailsInOrder(@Param("orderID") String orderID, @Param("productID") String productID, @Param("quantity") int quantity, @Param("productAmount") double productAmount, @Param("amountOwed") double amountOwed);

}

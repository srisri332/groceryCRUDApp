package com.grocery.backend.repository;

import com.grocery.backend.entity.NonPerishables;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NonPerishableRepository extends JpaRepository<NonPerishables, String> {
    @Transactional
    @Query(value = "SELECT productPrice FROM NonPerishables where productID=:productid")
    public double findPriceOfProduct(@Param("productid") String productid);

    @Transactional
    @Query(value = "SELECT productImage FROM NonPerishables where productID=:productid")
    public String findImageOfProduct(@Param("productid") String productid);

    @Transactional
    @Query(value = "SELECT productName FROM NonPerishables where productID=:productid")
    public String findNameOfProduct(@Param("productid") String productid);

    @Transactional
    public void deleteByProductID(String productID);
}

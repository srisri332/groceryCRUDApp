package com.grocery.backend.repository;

import com.grocery.backend.entity.Perishables;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerishableRepository extends JpaRepository<Perishables, String> {

    @Transactional
    @Query(value = "SELECT productPrice FROM Perishables where productID=:productid")
    public double findPriceOfProduct(@Param("productid") String productid);

    @Transactional
    @Query(value = "SELECT productImage FROM Perishables where productID=:productid")
    public String findImageOfProduct(@Param("productid") String productid);

    @Transactional
    @Query(value = "SELECT productName FROM Perishables where productID=:productid")
    public String findNameOfProduct(@Param("productid") String productid);

    @Transactional
    public void deleteByProductID(String productID);
}

package com.grocery.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


abstract public class Product {

    @Getter
    @Setter
    private String productName;

    @Getter
    @Setter
    private double productPrice;

    @Getter
    @Setter
    private String productImage;

}

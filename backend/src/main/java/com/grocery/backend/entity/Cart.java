package com.grocery.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Getter
    @Setter
    private String cartID = UUID.randomUUID().toString().substring(0, 10);

    @Getter
    @Setter
    private String userID;

    @Getter
    @Setter
    private double cartAmount;

    @Getter
    @Setter
    private ArrayList<Map<Product, Integer>> cartItems;

}

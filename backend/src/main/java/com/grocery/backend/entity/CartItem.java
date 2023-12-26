package com.grocery.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Table(name = "cart")
public class CartItem {
    @Id
    @Getter
    @Setter
    private String cartID = UUID.randomUUID().toString().substring(0, 10);

    @Getter
    @Setter
    private String userID;

    @Getter
    @Setter
    private double productAmount;

    @Getter
    @Setter
    private String productID;

    @Getter
    @Setter
    private int quantity;

}

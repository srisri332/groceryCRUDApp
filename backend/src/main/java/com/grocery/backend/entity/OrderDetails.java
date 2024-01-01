package com.grocery.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @Getter
    @Setter
    private String orderDetailsID = UUID.randomUUID().toString().substring(0, 10);

    @Getter
    @Setter
    private String orderID;

    @Getter
    @Setter
    private double productAmount;

    @Getter
    @Setter
    private String productID;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    private double amountOwed = 0;

}

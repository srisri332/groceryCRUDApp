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
@Table(name = "payment")
public class Payment {
    @Id
    @Getter
    @Setter
    private String paymentID= UUID.randomUUID().toString().substring(0, 10);

    @Getter
    @Setter
    private String orderID;

    @Getter
    @Setter
    private double amount;
}

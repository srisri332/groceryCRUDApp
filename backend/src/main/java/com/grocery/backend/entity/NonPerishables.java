package com.grocery.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "non_perishables")
public class NonPerishables extends Product {
    @Id
    @Getter
    @Setter
    private String productID = UUID.randomUUID().toString().substring(0, 10);

    @Getter
    @Setter
    private String productName;

    @Getter
    @Setter
    private double productPrice;

    @Getter
    @Setter
    private String productImage;

    @Getter
    @Setter
    private LocalDateTime manufacturedDate;

    @Getter
    @Setter
    private String category;
}

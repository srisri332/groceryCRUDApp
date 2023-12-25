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
@Table(name = "order")
public class Order {
    @Id
    @Getter
    @Setter
    private String orderID = UUID.randomUUID().toString().substring(0, 10);

    @Getter
    @Setter
    private String userID;

    @Getter
    @Setter
    private LocalDateTime orderDate;

    @Getter
    @Setter
    private String status;

}

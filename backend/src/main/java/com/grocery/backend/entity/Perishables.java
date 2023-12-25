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
@Table(name = "perishables")
public class Perishables extends Product {
    @Id
    @Getter
    @Setter
    private String productID = UUID.randomUUID().toString().substring(0, 10);
}

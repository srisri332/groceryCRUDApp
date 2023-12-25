package com.grocery.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "non_perishables")
public class NonPerishables extends Product{
}

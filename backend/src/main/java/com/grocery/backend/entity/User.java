package com.grocery.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @Getter
    @Setter
    private String userID= UUID.randomUUID().toString().substring(0, 10);

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String email;
}

package com.reveture.Project1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_types")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_type_id")
    private Long id;
    @Column(name = "account_type",nullable = false,unique = true,length = 10)
    private String type;
}


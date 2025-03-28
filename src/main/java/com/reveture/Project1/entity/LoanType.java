package com.reveture.Project1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loan_types")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LoanType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_type_id")
    private Long id;

    @Column(name = "loan_type",nullable = false, unique = true, length = 10)
    private String type;

    public LoanType(Long id) {
        this.id = id;
    }

}

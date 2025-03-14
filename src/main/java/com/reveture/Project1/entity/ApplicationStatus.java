package com.reveture.Project1.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_statuses")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ApplicationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_status_id")
    private Long id;

    @Column(name = "application_status",nullable = false, unique = true,length = 10)
    private String status;
}

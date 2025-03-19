package com.reveture.Project1.dto;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private Long accountId;  // ID de la cuenta asociada
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private int creditScore;
}
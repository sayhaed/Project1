package com.reveture.Project1.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(300)
    @Max(850)
    private int creditScore;
}
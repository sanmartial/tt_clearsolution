package org.globaroman.clearsolution.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.globaroman.clearsolution.validation.BirthDate;
import org.globaroman.clearsolution.validation.Phone;
import java.time.LocalDate;

@Data
public class CreateUserRequestDto {
    @NotBlank
    @Email(message = "Email is incorrect")
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    @BirthDate
    private LocalDate birthDate;
    @Valid
    private AddressRequestDto addressRequestDto;
    @NotBlank
    @Phone
    private String phone;
}

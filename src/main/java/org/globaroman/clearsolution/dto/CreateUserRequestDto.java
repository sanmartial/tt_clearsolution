package org.globaroman.clearsolution.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.globaroman.clearsolution.validation.BirthDate;
import org.globaroman.clearsolution.validation.Phone;

@Data
public class CreateUserRequestDto {
    @NotBlank
    @Email(message = "Email is incorrect")
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @BirthDate
    private String birthDate;
    @Valid
    private AddressRequestDto addressRequestDto;
    @NotBlank
    @Phone
    private String phone;
}

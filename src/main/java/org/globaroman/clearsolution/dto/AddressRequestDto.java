package org.globaroman.clearsolution.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressRequestDto {
    @NotBlank
    @Size(min = 5, max = 5,
            message = "The string must contain exactly 5 characters")
    private String postCode;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String building;
    private String apartment;
}

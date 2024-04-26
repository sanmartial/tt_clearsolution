package org.globaroman.clearsolution.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.globaroman.clearsolution.validation.Phone;

@Data
public class UpdatePhoneRequestDto {
    @NotBlank
    @Phone
    private String phone;
}

package org.globaroman.clearsolution.dto;

import java.time.LocalDate;
import lombok.Data;
import org.globaroman.clearsolution.model.Address;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;
    private String phone;
}

package org.globaroman.clearsolution.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;
    private String phone;
}

package org.globaroman.clearsolution.model;

import lombok.Data;

@Data
public class Address {
    private String postCode;
    private String city;
    private String street;
    private String building;
    private String apartment;
}

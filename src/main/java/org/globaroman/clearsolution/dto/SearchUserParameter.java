package org.globaroman.clearsolution.dto;

import lombok.Data;
import org.globaroman.clearsolution.validation.BirthDate;

@Data
public class SearchUserParameter {
    @BirthDate
    private String fromDate;
    @BirthDate
    private String toDate;
}

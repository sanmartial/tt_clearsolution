package org.globaroman.clearsolution.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.globaroman.clearsolution.validation.*;
import java.time.*;

@Data
@CheckDate(from = "fromDate", to = "toDate")
public class SearchUserParameter {
    @NotNull
    private LocalDate fromDate;
    @NotNull
    private LocalDate toDate;
}

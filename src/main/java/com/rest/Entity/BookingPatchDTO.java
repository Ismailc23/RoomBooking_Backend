package com.rest.Entity;

import lombok.Data;
import java.time.LocalDate;


@Data
public class BookingPatchDTO {
    private LocalDate bookedDate;

    private LocalDate stayStartDate;

    private LocalDate stayEndDate;
}

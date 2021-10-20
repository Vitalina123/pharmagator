package com.eleks.academy.pharmagator.dataproviders.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {

    @NotNull
    private long pharmacyId;

    @NotNull
    private long medicineId;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String externalId;

    @NotNull
    private Instant updatedAt;
}

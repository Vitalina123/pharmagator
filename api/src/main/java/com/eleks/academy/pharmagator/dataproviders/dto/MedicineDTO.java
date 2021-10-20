package com.eleks.academy.pharmagator.dataproviders.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {

    @NotNull
    private String title;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String externalId;
}

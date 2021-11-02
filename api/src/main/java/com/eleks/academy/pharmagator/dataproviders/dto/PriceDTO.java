package com.eleks.academy.pharmagator.dataproviders.dto;

import com.eleks.academy.pharmagator.entities.Price;
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

    public PriceDTO convertToDTO(Price price){
        return PriceDTO.builder()
                .pharmacyId(price.getPharmacyId())
                .medicineId(price.getMedicineId())
                .price(price.getPrice())
                .externalId(price.getExternalId())
                .updatedAt(price.getUpdatedAt())
                .build();
    }

    public Price convertToDB(PriceDTO price){
        return Price.builder()
                .pharmacyId(price.getPharmacyId())
                .medicineId(price.getMedicineId())
                .price(price.getPrice())
                .externalId(price.getExternalId())
                .updatedAt(price.getUpdatedAt())
                .build();
    }
}

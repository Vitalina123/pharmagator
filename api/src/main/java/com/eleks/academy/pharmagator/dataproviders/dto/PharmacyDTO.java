package com.eleks.academy.pharmagator.dataproviders.dto;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyDTO {

    @NotNull
    private String name;

    @NotNull
    private String medicineLinkTemplate;

    public PharmacyDTO convertToDTO(Pharmacy pharmacy){
        return PharmacyDTO.builder()
                .name(pharmacy.getName())
                .medicineLinkTemplate(pharmacy.getMedicineLinkTemplate())
                .build();
    }

    public Pharmacy convertToDB(PharmacyDTO pharmacy){
        return Pharmacy.builder()
                .name(pharmacy.getName())
                .medicineLinkTemplate(pharmacy.getMedicineLinkTemplate())
                .build();
    }
}

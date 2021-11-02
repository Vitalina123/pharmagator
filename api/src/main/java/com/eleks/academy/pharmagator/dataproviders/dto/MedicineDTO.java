package com.eleks.academy.pharmagator.dataproviders.dto;


import com.eleks.academy.pharmagator.entities.Medicine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {

    @NotNull
    private String title;

    public MedicineDTO convertToDTO(Medicine medicine){
        return MedicineDTO.builder()
                .title(medicine.getTitle())
                .build();
    }

    public Medicine convertToDB(MedicineDTO medicine){
        return Medicine.builder()
                .title(medicine.getTitle())
                .build();
    }
}

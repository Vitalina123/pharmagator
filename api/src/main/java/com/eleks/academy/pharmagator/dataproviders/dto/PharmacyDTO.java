package com.eleks.academy.pharmagator.dataproviders.dto;

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
}

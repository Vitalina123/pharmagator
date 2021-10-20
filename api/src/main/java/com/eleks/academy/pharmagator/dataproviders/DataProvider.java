package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDTO;

import java.util.stream.Stream;

public interface DataProvider {
    Stream<MedicineDTO> loadData();
}

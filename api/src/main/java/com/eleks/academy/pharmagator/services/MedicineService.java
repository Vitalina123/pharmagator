package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDTO;

import java.util.List;

public interface MedicineService {
    List<MedicineDTO> getAll();

    MedicineDTO getById(Long medicineId);

    MedicineDTO create(MedicineDTO medicine);

    MedicineDTO update(MedicineDTO medicine, Long medicineId);

    void delete(Long medicineId);
}

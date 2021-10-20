package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.PharmacyDTO;

import java.util.List;

public interface PharmacyService {
    List<PharmacyDTO> getAll();

    PharmacyDTO getById(Long pharmacyId);

    PharmacyDTO create(PharmacyDTO pharmacy);

    PharmacyDTO update(PharmacyDTO pharmacy);

    void delete(Long pharmacyId);
}

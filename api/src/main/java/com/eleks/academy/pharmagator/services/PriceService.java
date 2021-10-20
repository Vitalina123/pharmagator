package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.PriceDTO;

import java.util.List;

public interface PriceService {

    List<PriceDTO> getAll();

    PriceDTO getById(Long pharmacyId, Long medicineId);

    PriceDTO create(PriceDTO price);

    PriceDTO update(PriceDTO price, Long pharmacyId, Long medicineId);

    void delete(Long pharmacyId, Long medicineId);
}

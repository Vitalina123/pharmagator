package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.PriceDTO;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService{

    @Autowired
    private final PriceRepository priceRepository;
    @Override
    public List<PriceDTO> getAll() {
        return priceRepository.findAll().stream().map(price -> new PriceDTO().convertToDTO(price)).collect(Collectors.toList());
    }

    @Override
    public PriceDTO getById(Long pharmacyId, Long medicineId) {
        PriceId id = new PriceId(pharmacyId, medicineId);
        return new PriceDTO().convertToDTO(priceRepository.findById(id).get());
    }

    @Override
    public PriceDTO create(PriceDTO price) {
        return new PriceDTO().convertToDTO(priceRepository.save(new PriceDTO().convertToDB(price)));
    }

    @Override
    public PriceDTO update(PriceDTO price, Long pharmacyId, Long medicineId) {
        Price updatedPrice = null;
        PriceId id = new PriceId(pharmacyId, medicineId);
        Optional<Price> priceToUpdate = priceRepository.findById(id);
        if (priceToUpdate.isPresent()) {
            updatedPrice = priceToUpdate.get();
            updatedPrice.setPharmacyId(price.getPharmacyId());
            updatedPrice.setMedicineId(price.getMedicineId());
            updatedPrice.setPrice(price.getPrice());
            updatedPrice.setExternalId(price.getExternalId());
            updatedPrice.setUpdatedAt(price.getUpdatedAt());
        }
        return new PriceDTO().convertToDTO(priceRepository.save(updatedPrice));
    }

    @Override
    public void delete(Long pharmacyId, Long medicineId) {
        PriceId id = new PriceId(pharmacyId, medicineId);
        priceRepository.deleteById(id);
    }
}

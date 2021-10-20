package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.PharmacyDTO;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PharmacyServiceImpl implements PharmacyService {

    @Autowired
    private final ObjectMapper mapper;

    @Autowired
    private final PharmacyRepository pharmacyRepository;


    @Override
    public List<PharmacyDTO> getAll() {
        return pharmacyRepository.findAll().stream().map(pharmacy -> mapper.convertValue(pharmacy, PharmacyDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PharmacyDTO getById(Long pharmacyId) {
        return mapper.convertValue(pharmacyRepository.findById(pharmacyId), PharmacyDTO.class);
    }

    @Override
    public PharmacyDTO create(PharmacyDTO pharmacy) {
        return mapper.convertValue(pharmacyRepository.save(mapper.convertValue(pharmacy, Pharmacy.class)), PharmacyDTO.class);
    }

    @Override
    public PharmacyDTO update(PharmacyDTO pharmacy, Long pharmacyId) {
        Optional<Pharmacy> pharmacyToUpdate = pharmacyRepository.findById(pharmacyId);
        Pharmacy updatedPharmacy = null;
        if (pharmacyToUpdate.isPresent()) {
            updatedPharmacy = pharmacyToUpdate.get();
            updatedPharmacy.setId(pharmacyId);
            updatedPharmacy.setName(pharmacy.getName());
            updatedPharmacy.setMedicineLinkTemplate(pharmacy.getMedicineLinkTemplate());
        }
        return mapper.convertValue(pharmacyRepository.save(mapper.convertValue(updatedPharmacy, Pharmacy.class)), PharmacyDTO.class);
    }

    @Override
    public void delete(Long pharmacyId) {
        pharmacyRepository.deleteById(pharmacyId);
    }
}

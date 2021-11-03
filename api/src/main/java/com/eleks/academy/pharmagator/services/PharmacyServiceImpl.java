package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.PharmacyDTO;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
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
    private final PharmacyRepository pharmacyRepository;


    @Override
    public List<PharmacyDTO> getAll() {
        return pharmacyRepository.findAll().stream().map(pharmacy ->new PharmacyDTO().convertToDTO(pharmacy)).collect(Collectors.toList());
    }

    @Override
    public PharmacyDTO getById(Long pharmacyId) {
        return new PharmacyDTO().convertToDTO(pharmacyRepository.findById(pharmacyId).get());
    }

    @Override
    public PharmacyDTO create(PharmacyDTO pharmacy) {
        return new PharmacyDTO().convertToDTO(pharmacyRepository.save(pharmacy.convertToEntity()));
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
        return new PharmacyDTO().convertToDTO(pharmacyRepository.save(updatedPharmacy));
    }

    @Override
    public void delete(Long pharmacyId) {
        pharmacyRepository.deleteById(pharmacyId);
    }
}

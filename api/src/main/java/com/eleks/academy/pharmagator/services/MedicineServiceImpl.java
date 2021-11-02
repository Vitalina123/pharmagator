package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDTO;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
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
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private final MedicineRepository medicineRepository;

    @Override
    public List<MedicineDTO> getAll() {
        return medicineRepository.findAll().stream().map(medicine -> new MedicineDTO().convertToDTO(medicine)).collect(Collectors.toList());
    }

    @Override
    public MedicineDTO getById(Long medicineId) {
        return new MedicineDTO().convertToDTO(medicineRepository.findById(medicineId).get());
    }

    @Override
    public MedicineDTO create(MedicineDTO medicine) {
        return new MedicineDTO().convertToDTO(medicineRepository.save(new MedicineDTO().convertToDB(medicine)));
    }

    @Override
    public MedicineDTO update(MedicineDTO medicine, Long medicineId) {
        Medicine updatedMedicine = null;
        Optional<Medicine> medicineToUpdated = medicineRepository.findById(medicineId);
        if (medicineToUpdated.isPresent()) {
            updatedMedicine = medicineToUpdated.get();
            updatedMedicine.setTitle(medicine.getTitle());
        }
        return new MedicineDTO().convertToDTO(medicineRepository.save(updatedMedicine));

    }

    @Override
    public void delete(Long medicineId) {
        medicineRepository.deleteById(medicineId);
    }
}

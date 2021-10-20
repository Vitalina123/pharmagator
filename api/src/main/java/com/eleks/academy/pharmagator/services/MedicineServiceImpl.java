package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDTO;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
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
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private final ObjectMapper mapper;

    @Autowired
    private final MedicineRepository medicineRepository;

    @Override
    public List<MedicineDTO> getAll() {
        return medicineRepository.findAll().stream().map(medicine -> mapper.convertValue(medicine, MedicineDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MedicineDTO getById(Long medicineId) {
        return mapper.convertValue(medicineRepository.findById(medicineId), MedicineDTO.class);
    }

    @Override
    public MedicineDTO create(MedicineDTO medicine) {
        return mapper.convertValue(medicineRepository.save(mapper.convertValue(medicine, Medicine.class)), MedicineDTO.class);
    }

    @Override
    public MedicineDTO update(MedicineDTO medicine, Long medicineId) {
        Medicine updatedMedicine = null;
        Optional<Medicine> medicineToUpdated = medicineRepository.findById(medicineId);
        if (medicineToUpdated.isPresent()) {
            updatedMedicine = medicineToUpdated.get();
            updatedMedicine.setTitle(medicine.getTitle());
        }
        return mapper.convertValue(medicineRepository.save(mapper.convertValue(updatedMedicine, Medicine.class)), MedicineDTO.class);

    }

    @Override
    public void delete(Long medicineId) {
        medicineRepository.deleteById(medicineId);
    }
}

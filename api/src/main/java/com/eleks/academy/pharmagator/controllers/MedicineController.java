package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.entities.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineRepository medicineRepository;

    @GetMapping
    public ResponseEntity<List<Medicine>> getAll() {
        return ResponseEntity.ok(medicineRepository.findAll());
    }

    @GetMapping("/medicine/{id}")
    public ResponseEntity<Medicine> getEmployeeById(@PathVariable(value = "id") Long medicineId)
            throws Exception {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new Exception("Pharmacy not found for this id :: " + medicineId));
        return ResponseEntity.ok().body(medicine);
    }

    @PostMapping("/medicines")
    public ResponseEntity<Medicine> create(@RequestBody Medicine medicine) {
        return ResponseEntity.ok().body(medicineRepository.save(medicine));
    }

    @PutMapping("/medicine/{id}")
    public ResponseEntity<Medicine> update(@PathVariable(value = "id") Long medicineId,
                                           @RequestBody Medicine medicineDetails) throws Exception {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new Exception("Pharmacy not found for this id :: " + medicineId));

        medicine.setTitle(medicineDetails.getTitle());
        final Medicine updatedMedicine = medicineRepository.save(medicine);
        return ResponseEntity.ok(updatedMedicine);
    }

    @DeleteMapping("/medicine/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long medicineId)
            throws Exception {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new Exception("Employee not found for this id :: " + medicineId));

        medicineRepository.delete(medicine);
        return ResponseEntity.ok(true);
    }
}

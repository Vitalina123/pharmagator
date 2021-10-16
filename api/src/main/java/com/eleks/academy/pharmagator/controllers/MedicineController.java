package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.entities.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MedicineController {

    private final MedicineRepository medicineRepository;

    @GetMapping("/medicines")
    public ResponseEntity<List<Medicine>> getAll() {
        return ResponseEntity.ok(medicineRepository.findAll());
    }

    @GetMapping("/medicine/{id}")
    public ResponseEntity<Medicine> getById(@PathVariable(value = "id") Long medicineId) {
        Optional<Medicine> medicine = medicineRepository.findById(medicineId);
        return medicine.isPresent() ? new ResponseEntity(medicine.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/medicines")
    public ResponseEntity<Medicine> create(@RequestBody Medicine medicine) {
        try {
            Medicine createdMedicine = (medicineRepository.save(medicine));
            return new ResponseEntity<>(createdMedicine, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/medicine/{id}")
    public ResponseEntity<Medicine> update(@PathVariable(value = "id") Long medicineId,
                                           @RequestBody Medicine medicineDetails) {
        Optional<Medicine> medicine = medicineRepository.findById(medicineId);
        if (medicine.isPresent()) {
            Medicine updatedMedicine = medicine.get();
            updatedMedicine.setTitle(medicineDetails.getTitle());
            return new ResponseEntity<>(medicineRepository.save(updatedMedicine), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/medicine/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long medicineId) {
        try {
            medicineRepository.deleteById(medicineId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



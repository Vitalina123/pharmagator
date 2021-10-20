package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDTO;
import com.eleks.academy.pharmagator.services.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MedicineController {

    @Autowired
    private final MedicineService medicineService;

    @GetMapping("/medicines")
    public ResponseEntity<List<MedicineDTO>> getAll() {
        return ResponseEntity.ok(medicineService.getAll());
    }

    @GetMapping("/medicine/{id}")
    public ResponseEntity<MedicineDTO> getById(@PathVariable(value = "id") Long medicineId) {
        Optional<MedicineDTO> medicine = Optional.ofNullable(medicineService.getById(medicineId));
        return medicine.isPresent() ? new ResponseEntity(medicine.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/medicines")
    public ResponseEntity<MedicineDTO> create(@RequestBody MedicineDTO medicine) {
        try {
            MedicineDTO createdMedicine = (medicineService.create(medicine));
            return new ResponseEntity<>(createdMedicine, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/medicine/{id}")
    public ResponseEntity<MedicineDTO> update(@PathVariable(value = "id") Long medicineId,
                                           @RequestBody MedicineDTO medicineDetails) {
        try {
            MedicineDTO createdMedicine = (medicineService.update(medicineDetails, medicineId));
            return new ResponseEntity<>(createdMedicine, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/medicine/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long medicineId) {
        try {
            medicineService.delete(medicineId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



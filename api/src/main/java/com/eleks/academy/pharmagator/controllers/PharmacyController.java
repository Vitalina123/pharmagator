package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dataproviders.dto.PharmacyDTO;
import com.eleks.academy.pharmagator.services.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PharmacyController {
    private final PharmacyService pharmacyService;

    @GetMapping("/pharmacies")
    public ResponseEntity<List<PharmacyDTO>> getAll() {
        return ResponseEntity.ok(pharmacyService.getAll());
    }

    @GetMapping("/pharmacy/{id}")
    public ResponseEntity<PharmacyDTO> getById(@PathVariable(value = "id") Long pharmacyId) {
        Optional<PharmacyDTO> pharmacy = Optional.ofNullable(pharmacyService.getById(pharmacyId));

        return pharmacy.isPresent() ? new ResponseEntity(pharmacy.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/pharmacies")
    public ResponseEntity<PharmacyDTO> create(@RequestBody PharmacyDTO pharmacy) {
        try {
            PharmacyDTO createdPharmacy = pharmacyService.create(pharmacy);
            return new ResponseEntity<>(createdPharmacy, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pharmacy/{id}")
    public ResponseEntity<PharmacyDTO> update(@PathVariable(value = "id") Long pharmacyId,
                                           @RequestBody PharmacyDTO pharmacyDetails) {
        try {
            PharmacyDTO updatedPharmacy = pharmacyService.update(pharmacyDetails, pharmacyId);
            return updatedPharmacy.equals(null)?new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR):new ResponseEntity<>(updatedPharmacy, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/pharmacy/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long pharmacyId) {
        try {
            pharmacyService.delete(pharmacyId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

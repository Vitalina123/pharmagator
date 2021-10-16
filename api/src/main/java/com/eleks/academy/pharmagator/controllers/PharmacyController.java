package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
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
public class PharmacyController {
    private final PharmacyRepository pharmacyRepository;

    @GetMapping("/pharmacies")
    public ResponseEntity<List<Pharmacy>> getAll() {
        return ResponseEntity.ok(pharmacyRepository.findAll());
    }

    @GetMapping("/pharmacy/{id}")
    public ResponseEntity<Pharmacy> getById(@PathVariable(value = "id") Long pharmacyId) {
        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(pharmacyId);

        return pharmacy.isPresent() ? new ResponseEntity(pharmacy.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/pharmacies")
    public ResponseEntity<Pharmacy> create(@RequestBody Pharmacy pharmacy) {
        try {
            Pharmacy createdPharmacy = pharmacyRepository.save(pharmacy);
            return new ResponseEntity<>(createdPharmacy, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pharmacy/{id}")
    public ResponseEntity<Pharmacy> update(@PathVariable(value = "id") Long pharmacyId,
                                           @RequestBody Pharmacy pharmacyDetails) {
        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(pharmacyId);

        if (pharmacy.isPresent()) {
            Pharmacy updatedPharmacy = pharmacy.get();
            updatedPharmacy.setName(pharmacyDetails.getName());
            updatedPharmacy.setMedicineLinkTemplate(pharmacyDetails.getMedicineLinkTemplate());
            return new ResponseEntity<>(pharmacyRepository.save(updatedPharmacy), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/pharmacy/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long pharmacyId) {
        try {
            pharmacyRepository.deleteById(pharmacyId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

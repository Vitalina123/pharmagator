package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ResponseEntity<Pharmacy> getEmployeeById(@PathVariable(value = "id") Long pharmacyId)
            throws Exception {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new Exception("Pharmacy not found for this id :: " + pharmacyId));
        return ResponseEntity.ok().body(pharmacy);
    }

    @PostMapping("/pharmacies")
    public ResponseEntity<Pharmacy> create(@RequestBody Pharmacy pharmacy) {
        return ResponseEntity.ok().body(pharmacyRepository.save(pharmacy));
    }

    @PutMapping("/pharmacy/{id}")
    public ResponseEntity<Pharmacy> update(@PathVariable(value = "id") Long pharmacyId,
                                                   @RequestBody Pharmacy pharmacyDetails) throws Exception {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new Exception("Pharmacy not found for this id :: " + pharmacyId));

        pharmacy.setName(pharmacyDetails.getName());
        pharmacy.setMedicineLinkTemplate(pharmacyDetails.getMedicineLinkTemplate());
        final Pharmacy updatedPharmacy = pharmacyRepository.save(pharmacy);
        return ResponseEntity.ok(updatedPharmacy);
    }

    @DeleteMapping("/pharmacy/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long pharmacyId)
            throws Exception {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new Exception("Employee not found for this id :: " + pharmacyId));

        pharmacyRepository.delete(pharmacy);
        return ResponseEntity.ok(true);
    }
}

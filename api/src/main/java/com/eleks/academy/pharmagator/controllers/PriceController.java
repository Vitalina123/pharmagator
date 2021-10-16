package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.*;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
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
public class PriceController {

    private final PriceRepository priceRepository;

    @GetMapping("/prices")
    public ResponseEntity<List<Price>> getAll() {
        return ResponseEntity.ok(priceRepository.findAll());
    }

    @GetMapping("prices/{pharmacy-id}/{medicine-id}")
    public ResponseEntity<Price> getById(@PathVariable("pharmacy-id") Long pharmacyId, @PathVariable("medicine-id") Long medicineId) {
        PriceId id = new PriceId(pharmacyId, medicineId);
        Optional<Price> priceData = priceRepository.findById(id);
        return priceData.isPresent() ? new ResponseEntity(priceData.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/prices")
    public ResponseEntity<Price> create(@RequestBody Price price) {
        try {
            Price createdPrice = priceRepository.save(price);
            return new ResponseEntity<>(createdPrice, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("prices/{pharmacy-id}/{medicine-id}")
    public ResponseEntity<Price> update(@PathVariable("pharmacy-id") Long pharmacyId, @PathVariable("medicine-id") Long medicineId, @RequestBody Price priceDetails) {
        PriceId id = new PriceId(pharmacyId, medicineId);
        Optional<Price> price = priceRepository.findById(id);
        if (price.isPresent()) {
            Price updatedPrice = price.get();
            updatedPrice.setPharmacyId(priceDetails.getPharmacyId());
            updatedPrice.setMedicineId(priceDetails.getMedicineId());
            updatedPrice.setPrice(priceDetails.getPrice());
            updatedPrice.setExternalId(priceDetails.getExternalId());
            updatedPrice.setUpdatedAt(priceDetails.getUpdatedAt());
            return new ResponseEntity<>(priceRepository.save(updatedPrice), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("prices/{pharmacy-id}/{medicine-id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("pharmacy-id") Long pharmacyId, @PathVariable("medicine-id") Long medicineId) {
        try {
            PriceId id = new PriceId(pharmacyId, medicineId);
            priceRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

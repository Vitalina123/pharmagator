package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dataproviders.dto.PriceDTO;
import com.eleks.academy.pharmagator.services.PriceService;
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
public class PriceController {

    @Autowired
    private final PriceService priceService;

    @GetMapping("/prices")
    public ResponseEntity<List<PriceDTO>> getAll() {
        return ResponseEntity.ok(priceService.getAll());
    }

    @GetMapping("prices/{pharmacy-id}/{medicine-id}")
    public ResponseEntity<PriceDTO> getById(@PathVariable("pharmacy-id") Long pharmacyId, @PathVariable("medicine-id") Long medicineId) {
        Optional<PriceDTO> priceData = Optional.ofNullable(priceService.getById(pharmacyId, medicineId));
        return priceData.isPresent() ? new ResponseEntity(priceData.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/prices")
    public ResponseEntity<PriceDTO> create(@RequestBody PriceDTO price) {
        try {
            PriceDTO createdPrice = priceService.create(price);
            return new ResponseEntity<>(createdPrice, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("prices/{pharmacy-id}/{medicine-id}")
    public ResponseEntity<PriceDTO> update(@PathVariable("pharmacy-id") Long pharmacyId, @PathVariable("medicine-id") Long medicineId, @RequestBody PriceDTO priceDetails) {
        try {
            PriceDTO updatedPrice = priceService.update(priceDetails, pharmacyId, medicineId);
            return new ResponseEntity<>(updatedPrice, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("prices/{pharmacy-id}/{medicine-id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("pharmacy-id") Long pharmacyId, @PathVariable("medicine-id") Long medicineId) {
        try {
            priceService.delete(pharmacyId, medicineId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

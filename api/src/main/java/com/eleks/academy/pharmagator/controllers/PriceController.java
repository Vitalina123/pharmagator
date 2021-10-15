package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final PriceRepository priceRepository;

    @GetMapping("/prices")
    public ResponseEntity<List<Price>> getAll() {
        return ResponseEntity.ok(priceRepository.findAll());
    }

    @GetMapping("/price/{id}")
    public ResponseEntity<Price> getEmployeeById(@PathVariable(value = "id") Long priceId)
            throws Exception {
        Price price = priceRepository.findById(priceId)
                .orElseThrow(() -> new Exception("Pharmacy not found for this id :: " + priceId));
        return ResponseEntity.ok().body(price);
    }

    @PostMapping("/prices")
    public ResponseEntity<Price> create(@RequestBody Price price) {
        return ResponseEntity.ok().body(priceRepository.save(price));
    }

    @PutMapping("/price/{id}")
    public ResponseEntity<Price> update(@PathVariable(value = "id") Long priceId,
                                           @RequestBody Price priceDetails) throws Exception {
        Price price = priceRepository.findById(priceId)
                .orElseThrow(() -> new Exception("Pharmacy not found for this id :: " + priceId));

        price.setPharmacyId(priceDetails.getPharmacyId());
        price.setMedicineId(priceDetails.getMedicineId());
        price.setPrice(priceDetails.getPrice());
        price.setExternalId(priceDetails.getExternalId());
        price.setUpdatedAt(priceDetails.getUpdatedAt());
        final Price updatedPrice = priceRepository.save(price);
        return ResponseEntity.ok(updatedPrice);
    }

    @DeleteMapping("/price/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long priceId)
            throws Exception {
        Price price = priceRepository.findById(priceId)
                .orElseThrow(() -> new Exception("Employee not found for this id :: " + priceId));

        priceRepository.delete(price);
        return ResponseEntity.ok(true);
    }
}

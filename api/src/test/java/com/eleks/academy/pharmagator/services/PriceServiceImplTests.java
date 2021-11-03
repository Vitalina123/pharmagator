package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.PriceDTO;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class PriceServiceImplTests {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Test
    public void create_priceIsCreated(){
        var price = new Price(1, 1, new BigDecimal("23.5"), "23", Instant.now());
        Mockito.when(priceRepository.save(any(Price.class))).thenReturn(price);

        final var actualPrice = priceService.create(new PriceDTO(
                price.getPharmacyId(),
                price.getMedicineId(),
                price.getPrice(),
                price.getExternalId(),
                price.getUpdatedAt()));

        assertAll("Actual price is equals to expected",
                ()-> assertEquals(actualPrice.getPharmacyId(), price.getPharmacyId()),
                ()-> assertEquals(actualPrice.getMedicineId(), price.getMedicineId()),
                ()-> assertEquals(actualPrice.getPrice(), price.getPrice()),
                ()-> assertEquals(actualPrice.getExternalId(), price.getExternalId()),
                ()-> assertEquals(actualPrice.getUpdatedAt(), price.getUpdatedAt()));
    }

    @Test
    public void create_priceIsNotValid_pharmacyIdIsEqualNull_constraintViolationExceptionThrows(){
        var price = new Price(-1L, 1, new BigDecimal("23.5"), "23", Instant.now());
        Mockito.when(priceRepository.save(any(Price.class))).thenThrow(ConstraintViolationException.class);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->priceService.create(new PriceDTO().convertToDTO(price)));
    }

    @Test
    public void create_priceIsNotValid_medicineIdIsEqualNull_constraintViolationExceptionThrows(){
        var price = new Price(1, -1L, new BigDecimal("23.5"), "23", Instant.now());
        Mockito.when(priceRepository.save(any(Price.class))).thenThrow(ConstraintViolationException.class);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->priceService.create(new PriceDTO().convertToDTO(price)));
    }

    @Test
    public void create_priceIsNotValid_priceIsEqualNull_constraintViolationExceptionThrows(){
       var price = new Price(1, 1, null, "23", Instant.now());
        Mockito.when(priceRepository.save(any(Price.class))).thenThrow(ConstraintViolationException.class);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->priceService.create(new PriceDTO().convertToDTO(price)));
    }

    @Test
    public void create_priceIsNotValid_externalIdIsEqualNull_constraintViolationExceptionThrows(){
        var price = new Price(1, 1, new BigDecimal("23.5"), null, Instant.now());
        Mockito.when(priceRepository.save(any(Price.class))).thenThrow(ConstraintViolationException.class);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->priceService.create(new PriceDTO().convertToDTO(price)));
    }

    @Test
    public void create_riceIsNotValid_updatedAtIsEqualNull_constraintViolationExceptionThrows(){
        var price = new Price(1, 1, new BigDecimal("23.5"), "23", null);
        Mockito.when(priceRepository.save(any(Price.class))).thenThrow(ConstraintViolationException.class);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->priceService.create(new PriceDTO().convertToDTO(price)));
    }

    @Test
    public void create_priceIsNotValid_allFieldsAreNotValid_constraintViolationExceptionThrows(){
        var price = new Price(-1L, -1L, null, null, null);
        Mockito.when(priceRepository.save(any(Price.class))).thenThrow(ConstraintViolationException.class);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->priceService.create(new PriceDTO().convertToDTO(price)));
    }

    @Test
    public void getPriceById_priceIdIsNotValid_noSuchElementExceptionThrows(){
        Long medicineId = -1L;
        Long pharmacyId = 2L;
        PriceId priceId = new PriceId(pharmacyId, medicineId);
        Mockito.when(priceRepository.findById(priceId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->priceService.getById(pharmacyId, medicineId));
    }

    @Test
    public void getPriceById_pharmacyIdIsNotValid_noSuchElementExceptionThrows(){
        Long medicineId = 1L;
        Long pharmacyId = -2L;
        PriceId priceId = new PriceId(pharmacyId, medicineId);
        Mockito.when(priceRepository.findById(priceId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->priceService.getById(pharmacyId, medicineId));
    }

    @Test
    public void getPriceById_idsAreNotValid_noSuchElementExceptionThrows(){
        Long medicineId = -1L;
        Long pharmacyId = -2L;
        PriceId priceId = new PriceId(pharmacyId, medicineId);
        Mockito.when(priceRepository.findById(priceId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->priceService.getById(pharmacyId, medicineId));
    }

    @Test
    public void getPriceById_idsAreValid_priceFound(){
        Long medicineId = 1L;
        Long pharmacyId = 2L;
        PriceId priceId = new PriceId(pharmacyId, medicineId);
        Price price = new Price(1, 1, new BigDecimal("23.5"), "23", Instant.now());
        Mockito.when(priceRepository.findById(priceId)).thenReturn(java.util.Optional.of(price));

        final var actualPrice = priceService.getById(pharmacyId, medicineId);

        assertAll("Actual price is equals to expected",
                ()-> assertEquals(actualPrice.getPharmacyId(), price.getPharmacyId()),
                ()-> assertEquals(actualPrice.getMedicineId(), price.getMedicineId()),
                ()-> assertEquals(actualPrice.getPrice(), price.getPrice()),
                ()-> assertEquals(actualPrice.getExternalId(), price.getExternalId()),
                ()-> assertEquals(actualPrice.getUpdatedAt(), price.getUpdatedAt()));
    }

    @Test
    public void getAllPrices_pricesFound_returnList(){
        ArrayList<Price> prices = new ArrayList<>();
        prices.addAll(List.of(
                new Price(1, 1, new BigDecimal("23.5"), "23", Instant.now()),
                new Price(1, 2, new BigDecimal("24.5"), "33", Instant.now()),
                new Price(2, 1, new BigDecimal("25.5"), "43", Instant.now())
        ));

        Mockito.when(priceRepository.findAll())
                .thenReturn(prices);

        final var actualPrices = priceService.getAll();

        assertThat(actualPrices).matches(list->list.size() == prices.size());
        assertThat(actualPrices.containsAll(prices));
    }

    @Test
    public void getAllPrices_pricesNotFound_returnEmptyList(){
        Mockito.when(priceRepository.findAll()).thenReturn(List.of());

        final var prices = priceService.getAll();

        assertThat(prices).matches(list->list.size() == 0);
    }

    @Test
    public void getAllPrices_pricesNotFound_throwsNullPointerException(){
        Mockito.when(priceRepository.findAll()).thenReturn(null);

        assertThatExceptionOfType(NullPointerException.class).isThrownBy(()->priceService.getAll());
    }

    @Test
    public void deletePrice_priceIdIsValid_priceDeleted(){
        Long medicineId = 1L;
        Long pharmacyId = 2L;
        PriceId priceId = new PriceId(pharmacyId, medicineId);

        priceService.delete(pharmacyId, medicineId);

        verify(priceRepository, times(1)).deleteById(priceId);
    }
}

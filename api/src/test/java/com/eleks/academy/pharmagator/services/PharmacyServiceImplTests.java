package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.PharmacyDTO;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
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
public class PharmacyServiceImplTests {

    @Mock
    private PharmacyRepository pharmacyRepository;

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;

    @Test
    public void create_pharmacyIsCreated(){
        var pharmacy = new Pharmacy(1, "some name", "link");
        Mockito.when(pharmacyRepository.save(any(Pharmacy.class))).thenReturn(pharmacy);

        final var actualPharmacy = pharmacyService.create(new PharmacyDTO(pharmacy.getName(), pharmacy.getMedicineLinkTemplate()));


        assertAll("Actual pharmacy is equals to expected",
                ()-> assertEquals(actualPharmacy.getName(), pharmacy.getName()),
                ()-> assertEquals(actualPharmacy.getMedicineLinkTemplate(), pharmacy.getMedicineLinkTemplate()));
    }

    @Test
    public void create_pharmacyIsNotValid_linkIsEqualNull_constraintViolationExceptionThrows(){
        var pharmacy = new Pharmacy(1, null, "link");
        Mockito.when(pharmacyRepository.save(any(Pharmacy.class))).thenThrow(ConstraintViolationException.class);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->pharmacyService.create(new PharmacyDTO().convertToDTO(pharmacy)));
    }

    @Test
    public void create_pharmacyIsNotValid_nameIsEqualNull_constraintViolationExceptionThrows(){
        var pharmacy = new Pharmacy(1, "name", null);
        Mockito.when(pharmacyRepository.save(any(Pharmacy.class))).thenThrow(ConstraintViolationException.class);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->pharmacyService.create(new PharmacyDTO().convertToDTO(pharmacy)));
    }

    @Test
    public void getPharmacyById_pharmacyIdIsNotValid_noSuchElementExceptionThrows(){
        Long pharmacyId = -2L;
        Mockito.when(pharmacyRepository.findById(pharmacyId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->pharmacyService.getById(pharmacyId));
    }

    @Test
    public void getPharmacyById_pharmacyIdIsValid_pharmacyFound(){
        Long pharmacyId = 1L;
        Pharmacy pharmacy = new Pharmacy(1, "some name", "link");
        Mockito.when(pharmacyRepository.findById(pharmacyId)).thenReturn(Optional.of(pharmacy));

        final var actualPharmacy = pharmacyService.getById(pharmacyId);

        assertEquals(pharmacy.getName(), actualPharmacy.getName());
        assertEquals(pharmacy.getMedicineLinkTemplate(), actualPharmacy.getMedicineLinkTemplate());
    }

    @Test
    public void getAllPharmacies_pharmaciesFound_returnList(){
        ArrayList<Pharmacy> pharmacies = new ArrayList<>();
        pharmacies.addAll(List.of(
                new Pharmacy(1, "name", "link"),
                new Pharmacy(2, "name2", "link2"),
                new Pharmacy(3, "name3", "link3")
        ));
        Mockito.when(pharmacyRepository.findAll())
                .thenReturn(pharmacies);

        final var actualPharmacies = pharmacyService.getAll();

        assertThat(actualPharmacies).matches(list->list.size() == pharmacies.size());
        assertThat(actualPharmacies.containsAll(pharmacies));
    }

    @Test
    public void getAllPharmacies_pharmaciesNotFound_returnEmptyList(){
        Mockito.when(pharmacyRepository.findAll()).thenReturn(List.of());

        final var pharmacies = pharmacyService.getAll();

        assertThat(pharmacies).matches(list->list.size() == 0);
    }

    @Test
    public void getAllPharmacies_pharmaciesNotFound_throwsNullPointerException(){
        Mockito.when(pharmacyRepository.findAll()).thenReturn(null);

        assertThatExceptionOfType(NullPointerException.class).isThrownBy(()->pharmacyService.getAll());
    }

    @Test
    public void deletePharmacy_pharmacyIdIsValid_medicineDeleted(){
        Long pharmacyId = 2L;

        pharmacyService.delete(pharmacyId);

        verify(pharmacyRepository, times(1)).deleteById(pharmacyId);
    }
}

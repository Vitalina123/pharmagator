package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDTO;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class MedicineServiceImplTests {

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private MedicineServiceImpl medicineService;

    @Test
    public void create_medicineIsCreated(){
        var medicine = new Medicine(1, "some title");
        Mockito.when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine);

        final var actualMedicine = medicineService.create(new MedicineDTO(medicine.getTitle()));

        assertThat(actualMedicine.getTitle()).isEqualTo(medicine.getTitle());
    }

    @Test
    public void create_medicineIsNotValid_constraintViolationExceptionThrows(){
        var medicine = new Medicine(1, null);
        Mockito.when(medicineRepository.save(any(Medicine.class))).thenThrow(ConstraintViolationException.class);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->medicineService.create(new MedicineDTO().convertToDTO(medicine)));
    }

    @Test
    public void getMedicineById_medicineIdIsNotValid_noSuchElementExceptionThrows(){
        Long medicineId = -2L;
        Mockito.when(medicineRepository.findById(medicineId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->medicineService.getById(medicineId));
    }

    @Test
    public void getMedicineById_medicineIdIsValid_medicineFound(){
        Long medicineId = 1L;
        Medicine medicine = new Medicine(1, "someTitle");
        Mockito.when(medicineRepository.findById(medicineId)).thenReturn(java.util.Optional.of(medicine));

        final var actualMedicine = medicineService.getById(medicineId);

        assertEquals(medicine.getTitle(), actualMedicine.getTitle());
    }

    @Test
    public void getAllMedicines_medicinesFound_returnList(){
        ArrayList<Medicine> medicines = new ArrayList<>();
        medicines.addAll(List.of(
                new Medicine(1, "testTitle"),
                new Medicine(2, "testTitle2"),
                new Medicine(3, "testTitle3")
        ));
        Mockito.when(medicineRepository.findAll())
                .thenReturn(medicines);

        final var actualMedicines = medicineService.getAll();

        assertThat(actualMedicines).matches(list->list.size() == medicines.size());
        assertThat(actualMedicines.containsAll(medicines));
    }

    @Test
    public void getAllMedicines_medicinesNotFound_returnEmptyList(){
        Mockito.when(medicineRepository.findAll()).thenReturn(List.of());

        final var medicines = medicineService.getAll();

        assertThat(medicines).matches(list->list.size() == 0);
    }

    @Test
    public void getAllMedicines_medicinesNotFound_throwsNullPointerException(){
        Mockito.when(medicineRepository.findAll()).thenReturn(null);

        assertThatExceptionOfType(NullPointerException.class).isThrownBy(()->medicineService.getAll());
    }

    @Test
    public void deleteMedicine_medicineIdIsValid_medicineDeleted(){
        Long medicineId = 2L;

        medicineService.delete(medicineId);

        verify(medicineRepository, times(1)).deleteById(medicineId);
    }
}

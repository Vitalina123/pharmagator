package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class DataProviderImpl implements DataProvider {
    @Override
    public Stream<MedicineDTO> loadData() {
        return IntStream.rangeClosed(1, 100)
                .mapToObj(this::buildDto);
    }

    private MedicineDTO buildDto(int i) {
        return MedicineDTO.builder()
                .externalId(String.valueOf(i))
                .title("title" + i)
                .price(BigDecimal.valueOf(Math.random()))
                .build();
    }
}

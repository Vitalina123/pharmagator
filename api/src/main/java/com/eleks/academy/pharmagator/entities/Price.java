package com.eleks.academy.pharmagator.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prices")
@IdClass(PriceId.class)
public class Price {
    @Id
    private long pharmacyId;
    @Id
    private long medicineId;

    @Column(name="price", nullable=false)
    private BigDecimal price;

    @Column(name="external_id", nullable=false)
    private String externalId;

    @Column(name="updated_at", nullable=false)
    private Instant updatedAt;
}

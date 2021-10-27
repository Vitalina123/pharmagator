package com.eleks.academy.pharmagator.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pharmacies")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", length=256, nullable=false, unique=true)
    private String name;

    @Column(name="medicine_link_template", length=256, nullable=false, unique=true)
    private String medicineLinkTemplate;
}

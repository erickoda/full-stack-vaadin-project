package com.example.demo.backend.vehicle;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cars")
public class VehicleEntity {
    
    @EqualsAndHashCode.Include
    @NotBlank
    @Id
    private String licensePlate;
    
    @NotBlank
    private String builder;
    @NotBlank
    private String model;
    @NotBlank
    private String colour;
    @NotNull
    private Integer yearOfFabrication;
    @NotBlank
    private VehicleTier tier;
    @NotBlank
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    private String reasonToDelete;
}

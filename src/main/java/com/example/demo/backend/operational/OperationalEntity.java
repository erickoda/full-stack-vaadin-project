package com.example.demo.backend.operational;

import com.example.demo.backend.vehicle.VehicleTier;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "operational")
public class OperationalEntity
{
    @EqualsAndHashCode.Include
    @NotNull
    @Enumerated(EnumType.STRING)
    @Id
    private VehicleTier tier;

    @NotNull
    private int dailyRent;

    @NotNull
    private int fuelFillValue;

    @NotNull
    private int exteriorCleaningValue;

    @NotNull
    private int interiorCleaningValue;

    @NotNull
    private int insuranceDailyValue;
}

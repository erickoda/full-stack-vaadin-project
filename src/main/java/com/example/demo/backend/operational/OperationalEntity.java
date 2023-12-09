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

    public VehicleTier getTier() {
        return this.tier;
    }

    public int getDailyRent() {
        return this.dailyRent;
    }

    public int getFuelFillValue() {
        return this.fuelFillValue;
    }

    public int getExteriorCleaningValue() {
        return this.exteriorCleaningValue;
    }

    public int getInteriorCleaningValue() {
        return this.interiorCleaningValue;
    }

    public int getInsuranceDailyValue() {
        return this.insuranceDailyValue;
    }

    public boolean check() {
        if (
            this.tier == null || this.dailyRent < 0 || this.fuelFillValue < 0 ||
            this.exteriorCleaningValue < 0 || this.interiorCleaningValue < 0 ||
            this.insuranceDailyValue < 0
        ) { return false;}

        return true;
    }
}

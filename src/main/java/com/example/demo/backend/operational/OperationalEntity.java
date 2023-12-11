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

    /**
     * Returns the tier of the vehicle
    */
    public VehicleTier getTier() {
        return this.tier;
    }

    /**
     * Gets the daily rent
     * 
     * @return      the daily rent
     * @see         Integer
    */
    public int getDailyRent() {
        return this.dailyRent;
    }

    /**
     * Gets the fuel fill value
     * 
     * @return      the fuel fill value
     * @see         Integer
    */
    public int getFuelFillValue() {
        return this.fuelFillValue;
    }

    /**
     * Gets the Exterior cleaning value
     * 
     * @return      the exterior cleaning value
     * @see         Integer
    */
    public int getExteriorCleaningValue() {
        return this.exteriorCleaningValue;
    }

    /**
     * Gets the Interior cleaning value
     * 
     * @return      the Interior cleaning value
     * @see         Integer
    */
    public int getInteriorCleaningValue() {
        return this.interiorCleaningValue;
    }

    /**
     * Gets the Insurance Daily Value
     * 
     * @return      the Insurance Daily Value
     * @see         Integer
    */
    public int getInsuranceDailyValue() {
        return this.insuranceDailyValue;
    }

    /**
     * Checks if the operational entity is valid
     * 
     * @return      a boolean that represents if the entity is valid
     * @see         boolean
    */
    public boolean check() {
        if (
            this.tier == null || this.dailyRent < 0 || this.fuelFillValue < 0 ||
            this.exteriorCleaningValue < 0 || this.interiorCleaningValue < 0 ||
            this.insuranceDailyValue < 0
        ) { return false;}

        return true;
    }
}

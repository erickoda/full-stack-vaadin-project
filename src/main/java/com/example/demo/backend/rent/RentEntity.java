package com.example.demo.backend.rent;

import java.time.LocalDate;

import com.example.demo.backend.operational.OperationalEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "rent")
public class RentEntity
{
    @EqualsAndHashCode.Include
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @NotBlank
    private String cpf;

    @NotBlank
    private String licensePlate;

    @NotNull
    private LocalDate takeOutDate;

    @NotNull
    private LocalDate returnDate;

    @NotNull
    private int rentValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RentStatus status;

    @NotNull
    private boolean cleanExterior;
    
    @NotNull
    private boolean cleanInterior;

    @NotNull
    private boolean hasInsurance;

    /**
     * Constructor to the RentEntity class
    */
    public RentEntity(RentStatus status) {
        this.status = status;
    }

    /**
     * Gets License Plate of the RentEntity
     * 
     * @return      a String with the License Plate
     * @see         String
    */
    public String getLicensePlate() {
        return this.licensePlate;
    }

    /**
     * Gets Take Out LocalDate of the RentEntity
     * 
     * @return      a LocalDate with the Take Out Date
     * @see         LocalDate
    */
    public LocalDate getTakeOutDate() {
        return this.takeOutDate;
    }

    /**
     * Gets Return LocalDate of the RentEntity
     * 
     * @return      a LocalDate with the Return Date
     * @see         LocalDate
    */
    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    /**
     * Gets Rent Value of the RentEntity
     * 
     * @return      the rentStatus
     * @see         int
    */
    public RentStatus getStatus() {
        return this.status;
    }

    /**
     * Gets Clean Exterior of the RentEntity
     * 
     * @return      a boolean with the Clean Exterior
     * @see         boolean
    */
    public boolean getCleanExterior() {
        return this.cleanExterior;
    }

    /**
     * Gets Clean Interior of the RentEntity
     * 
     * @return      a boolean with the Clean Interior
     * @see         boolean
    */
    public boolean getCleanInterior() {
        return this.cleanInterior;
    }

    /**
     * Gets Has Insurance of the RentEntity
     * 
     * @return      a boolean with the Has Insurance
     * @see         boolean
    */
    public boolean getHasInsurance() {
        return this.hasInsurance;
    }
}

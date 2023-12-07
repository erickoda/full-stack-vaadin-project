package com.example.demo.backend.rent;

import java.time.LocalDate;

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
import lombok.Generated;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}

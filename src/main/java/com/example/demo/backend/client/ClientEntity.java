package com.example.demo.backend.client;

// import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
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
@Table(name = "clients")
public class ClientEntity {

    @EqualsAndHashCode.Include
    @NotBlank
    @Id
    private String  cpf;

    @NotBlank
    private String  name;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String  email;

    @NotBlank
    private String  mobileNumber;
}

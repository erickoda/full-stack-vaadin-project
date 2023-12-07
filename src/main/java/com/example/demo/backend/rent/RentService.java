package com.example.demo.backend.rent;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentService implements CrudListener<RentEntity>
{
    private final RentRepository rentRepository;
    
    @Override
    public Collection<RentEntity> findAll() {
        return rentRepository.findAll();
    }

    @Override
    public RentEntity add(RentEntity rent) {
        if (!validateRent(rent))
        {
            return null;
        }

        return rentRepository.save(rent);
    }

    @Override
    public RentEntity update(RentEntity rent) {
        if (!validateRent(rent))
        {
            return null;
        }
        
        return rentRepository.save(rent);
    }

    @Override
    public void delete(RentEntity rent) {
        rentRepository.delete(rent);
    }

    private boolean validateLicensePlate(String plate)
    {
        if (plate.length() != 7) {
            return false;
        }

        for (int i = 0; i < plate.length(); i++) {
            if (i < 3) {
                if (Character.isDigit(plate.charAt(i)) == true) {
    
                    return false;
                }
            }
            else if (i == 4) {
                if (Character.isDigit(plate.charAt(i)) == true) {
    
                    return false;
                }
            } else {
                if (Character.isDigit(plate.charAt(i)) == false) {
    
                    return false;
                }
            }
        }

        return true;
    }

    private boolean validateCPF(String cpf)
    {
        if (cpf.length() != 11) return false;

        return true;
    }

    private boolean validateRent(RentEntity rent)
    {
        return validateCPF(rent.getCpf()) && 
            validateLicensePlate(rent.getLicensePlate()) &&
            (rent.getStatus() != null);
    }
}

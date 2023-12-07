package com.example.demo.backend.rent;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.example.demo.backend.operational.OperationalEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentService implements CrudListener<RentEntity>
{
    private final RentRepository rentRepository;
    
    /**
     * Returns a Collection of RentEntity objects stored in the database.
     * 
     * @return  a Collection of RentEntity
     * @see     Collection
     */
    @Override
    public Collection<RentEntity> findAll() {
        return rentRepository.findAll();
    }
    
    /**
     * Returns the RentEntity that is to be added if it is not
     * invalid. Else returns a null object.
     * 
     * @param   rent    an RentEntity to be added to the database
     * @return          a RentEntity
     * @see             RentEntity
     */
    @Override
    public RentEntity add(RentEntity rent) {
        if (!validateRent(rent))
        {
            return null;
        }

        return rentRepository.save(rent);
    }

    /**
     * Returns the RentEntity that is to be ipdated if it is not
     * invalid. Else returns a null object.
     * 
     * @param   rent    an RentEntity to be updated to the database
     * @return          a RentEntity
     * @see             RentEntity
     */
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

    /**
     * Checks if the license plate is valid.
     * 
     * @param   plate   a license plate String to be validated 
     * @return          true if valid, false if invalid
     * @see             boolean
     */
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

    /**
     * Checks if the lCPF is valid.
     * 
     * @param   cpf     a cpf String to be validated 
     * @return          true if valid, false if invalid
     * @see             boolean
     */
    private boolean validateCPF(String cpf)
    {
        if (cpf.length() != 11) return false;

        return true;
    }

    /**
     * Checks if the RentEntity is valid.
     * 
     * @param   rent    a RentEntity to be validated 
     * @return          true if valid, false if invalid
     * @see             boolean
     */
    private boolean validateRent(RentEntity rent)
    {
        return validateCPF(rent.getCpf()) && 
            validateLicensePlate(rent.getLicensePlate()) &&
            (rent.getStatus() != null);
    }
}

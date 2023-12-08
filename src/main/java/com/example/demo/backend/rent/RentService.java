package com.example.demo.backend.rent;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.example.demo.backend.operational.OperationalEntity;
import com.example.demo.backend.operational.OperationalRepository;
import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.backend.vehicle.VehicleRepository;
import com.example.demo.backend.vehicle.VehicleStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentService implements CrudListener<RentEntity>
{
    private final RentRepository rentRepository;
    private final VehicleRepository vehicleRepository;
    private final OperationalRepository operationalRepository;
    
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

    public List<VehicleEntity> findUnrentedVehicles(LocalDate takeOutDate, LocalDate returnDate)
    {
        var vehicles = vehicleRepository.findAll();
        var rents = rentRepository.findAll();

        List<String> unavailableVehicles = new ArrayList<String>();

        for (int i = 0; i < rents.size(); i++)
        {
            if (
                (takeOutDate.isBefore(rents.get(i).getTakeOutDate()) && 
                 returnDate.isAfter(rents.get(i).getTakeOutDate()))
                || 
                (takeOutDate.isAfter(rents.get(i).getTakeOutDate()) &&
                 returnDate.isBefore(rents.get(i).getReturnDate()))
                ||
                (takeOutDate.isBefore(rents.get(i).getReturnDate()) &&
                 returnDate.isAfter(rents.get(i).getReturnDate()))
                ||
                (takeOutDate.isBefore(rents.get(i).getTakeOutDate()) &&
                 returnDate.isAfter(rents.get(i).getReturnDate()))
                )
            {
                unavailableVehicles.add(rents.get(i).getLicensePlate());
            }
        }

        for (int i = 0; i < vehicles.size(); i++)
        {
            if (unavailableVehicles.contains(vehicles.get(i).getLicensePlate()) 
                || vehicles.get(i).getStatus() == VehicleStatus.DELETADO
                || vehicles.get(i).getStatus() == VehicleStatus.INDISPONIVEL
                || vehicles.get(i).getStatus() == VehicleStatus.LOCADO
                || vehicles.get(i).getStatus() == VehicleStatus.RESERVADO)
            {
                vehicles.remove(i);
            }
        }

        return vehicles;
    }

    public List<String> getUnrentedVehiclesPlates(List<VehicleEntity> availableVehicles)
    {
        List<String> vehiclesPlates = new ArrayList<String>();
        
        for (int i = 0; i < availableVehicles.size(); i++)
        {
            vehiclesPlates.add(availableVehicles.get(i).getLicensePlate());
        }

        return vehiclesPlates;
    }

    public int calculateRentPrice(RentEntity rent)
    {
        var vehicles = vehicleRepository.findAll();
        var operational = operationalRepository.findAll();

        VehicleEntity thisVehicle = null;
        OperationalEntity thisOperational = null;

        for (int i = 0; i < vehicles.size(); i++)
        {
            if (vehicles.get(i).getLicensePlate() == rent.getLicensePlate())
            {
                thisVehicle = vehicles.get(i);
                break;
            }
        }

        for (int i = 0; i < operational.size(); i++)
        {
            if (operational.get(i).getTier() == thisVehicle.getTier())
            {
                thisOperational = operational.get(i);
                break;
            }
        }
        
        long totalRent = ChronoUnit.DAYS.between(rent.getTakeOutDate(), rent.getReturnDate()) * 
             thisOperational.getDailyRent();

        if (rent.isCleanExterior())
        {
            totalRent += thisOperational.getExteriorCleaningValue();
        }
        if (rent.isCleanInterior())
        {
            totalRent += thisOperational.getInteriorCleaningValue();
        }
        if (rent.isHasInsurance())
        {
            totalRent += ChronoUnit.DAYS.between(rent.getTakeOutDate(), rent.getReturnDate()) *
                thisOperational.getInsuranceDailyValue();
        }

        return (int) totalRent;
    }
}

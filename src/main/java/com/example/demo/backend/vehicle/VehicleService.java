package com.example.demo.backend.vehicle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService implements CrudListener<VehicleEntity> {

    private final VehicleRepository vehicleRepository;
    
    /**
     * Function returns a Collection of all VehicleEntity objects stored in the database.
     * 
     * @return  Collection<VehicleEntity>   a Collection of VehicleEntity
     * @see     Collection<VehicleEntity>
    */
    @Override
    public Collection<VehicleEntity> findAll() {

        var allVehicles = vehicleRepository.findAll();
        var filteredVehicles = new ArrayList<VehicleEntity>();

        for (var vehicle: allVehicles) {
            if (vehicle.getStatus() != VehicleStatus.DELETADO) {
                filteredVehicles.add(vehicle);
            }
        }
        
        return filteredVehicles;
    }

    /**
     * Function inserts a Vehicle into the data base
     * 
     * @param   vehicle         a VehicleEntity to be added to the database
     * @return  VehicleEntity   a Collection of VehicleEntity
     * @see     VehicleEntity
    */
    @Override
    public VehicleEntity add(VehicleEntity vehicle) {
        return vehicleRepository.save(vehicle);
    }

    /**
     * Function updates a Vehicle into the data base
     * 
     * @param   vehicle         a VehicleEntity to be updated to the database
     * @return  VehicleEntity   a Collection of VehicleEntity
     * @see     VehicleEntity
    */
    @Override
    public VehicleEntity update(VehicleEntity vehicle) {
        return vehicleRepository.save(vehicle);
    }

    /**
     * Function deletes a Vehicle into the data base
     * 
     * @param   vehicle         a VehicleEntity to be deleted to the database
     * @return  VehicleEntity   a Collection of VehicleEntity
     * @see     VehicleEntity
    */
    @Override
    public void delete(VehicleEntity vehicle) {
        vehicleRepository.delete(vehicle);
    }

    /**
     * Function returns a List of the chosen VehicleEntity
     * objects stored in the database by the tier
     * 
     * @param   availableVehicles     a List of VehicleEntity
     * @param   tier                  a tier of VehicleTier
     * @return  List<VehicleEntity>   a Collection of VehicleEntity
     * @see     List<VehicleEntity>
    */
    public List<VehicleEntity> getVehicle(List<VehicleEntity> availableVehicles, VehicleTier tier)
    {
        // var repo = vehicleRepository.findAll();
        List<VehicleEntity> wantedVehicle = new ArrayList<VehicleEntity>();

        for (int i = 0; i < availableVehicles.size(); i++)
        {
            if (availableVehicles.get(i).getTier() == tier
                && availableVehicles.get(i).getStatus() == VehicleStatus.DISPONIVEL)
            {
                wantedVehicle.add(availableVehicles.get(i));
            }
        }

        return wantedVehicle;
    }

    /**
     * Function returns a float that represents the
     * percentage of the car by the chosen status
     * 
     * @param   status  a Vehicle Status
     * @return          float
     * @see             float
    */
    public float percentageByStatus(VehicleStatus status) {
        int counter = 0;
        var allVehicles = vehicleRepository.findAll();
        
        for (var vehicle: allVehicles) {
            if (vehicle.getStatus() == status) {
                counter++;
            }
        }
        
        return (float) counter/allVehicles.size();
    }

    /**
     * Function returns a float that represents the
     * percentage of the car by the chosen tier
     * 
     * @param   tier    a Vehicle Tier
     * @return          float
     * @see             float
    */
    public float percentageByTier(VehicleTier tier) {
        int counter = 0;
        var allVehicles = vehicleRepository.findAll();
        
        for (var vehicle: allVehicles) {
            if (vehicle.getTier() == tier) {
                counter++;
            }
        }
        
        return (float) counter/allVehicles.size();
    }
}

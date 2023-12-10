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

    @Override
    public VehicleEntity add(VehicleEntity vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public VehicleEntity update(VehicleEntity vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void delete(VehicleEntity vehicle) {
        vehicleRepository.delete(vehicle);
    }

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
}

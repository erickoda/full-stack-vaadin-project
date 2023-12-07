package com.example.demo.backend.vehicle;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService implements CrudListener<VehicleEntity> {

    private final VehicleRepository vehicleRepository;
    
    @Override
    public Collection<VehicleEntity> findAll() {
        return vehicleRepository.findAll();
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
}

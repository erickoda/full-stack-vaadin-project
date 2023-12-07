package com.example.demo.backend.vehicle;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService implements CrudListener<VehicleEntity> {

    private final VehicleRepository carsRepository;
    
    @Override
    public Collection<VehicleEntity> findAll() {
        return carsRepository.findAll();
    }

    @Override
    public VehicleEntity add(VehicleEntity car) {
        return carsRepository.save(car);
    }

    @Override
    public VehicleEntity update(VehicleEntity car) {
        return carsRepository.save(car);
    }

    @Override
    public void delete(VehicleEntity car) {
        carsRepository.delete(car);
    }
}

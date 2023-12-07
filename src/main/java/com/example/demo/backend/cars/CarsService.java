package com.example.demo.backend.cars;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarsService implements CrudListener<CarsEntity> {

    private final CarsRepository carsRepository;
    
    @Override
    public Collection<CarsEntity> findAll() {
        return carsRepository.findAll();
    }

    @Override
    public CarsEntity add(CarsEntity car) {
        return carsRepository.save(car);
    }

    @Override
    public CarsEntity update(CarsEntity car) {
        return carsRepository.save(car);
    }

    @Override
    public void delete(CarsEntity car) {
        carsRepository.delete(car);
    }
}

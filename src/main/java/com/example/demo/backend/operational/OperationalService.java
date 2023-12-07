package com.example.demo.backend.operational;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperationalService implements CrudListener<OperationalEntity>
{
    private final OperationalRepository operationalRepository;
    
    @Override
    public Collection<OperationalEntity> findAll() {
        return operationalRepository.findAll();
    }

    @Override
    public OperationalEntity add(OperationalEntity operational) {
        if (validateData(operational))
        {
            return null;
        }

        return operationalRepository.save(operational);
    }

    @Override
    public OperationalEntity update(OperationalEntity operational) {
        if (validateData(operational))
        {
            return null;
        }

        return operationalRepository.save(operational);
    }

    @Override
    public void delete(OperationalEntity operational) {

        operationalRepository.delete(operational);
    }

    private boolean validateData(OperationalEntity operational)
    {
        if (operational.getDailyRent() < 0 ||
            operational.getExteriorCleaningValue() < 0 ||
            operational.getFuelFillValue() < 0 ||
            operational.getInsuranceDailyValue() < 0 ||
            operational.getInteriorCleaningValue() < 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}

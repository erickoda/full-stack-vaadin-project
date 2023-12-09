package com.example.demo.backend.operational;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperationalService implements CrudListener<OperationalEntity>
{
    private final OperationalRepository operationalRepository;
    
    /**
     * Returns a Collection of all OperationalEntity objects stored in the database.
     * 
     * @return  a Collection of OperationalEntity
     * @see     Collection
     */
    @Override
    public Collection<OperationalEntity> findAll() {
        return operationalRepository.findAll();
    }

    /**
     * Returns the OperationalEntity that is to be added if it is not
     * invalid. Else returns a null object.
     * 
     * @param   operational     an OperationalEntity to be added to the database
     * @return                  an OperationalEntity
     * @see                     OperationalEntity
     */
    @Override
    public OperationalEntity add(OperationalEntity operational) {
        // if (validateData(operational))
        // {
        //     return null;
        // }

        return operationalRepository.save(operational);
    }

    /**
     * Returns the OperationalEntity that is to be updated if it is not
     * invalid. Else returns a null object.
     * 
     * @param   operational     an OperationalEntity to be updated to the database
     * @return                  an OperationalEntity
     * @see                     OperationalEntity
     */
    @Override
    public OperationalEntity update(OperationalEntity operational) {
        // if (validateData(operational))
        // {
        //     return null;
        // }

        return operationalRepository.save(operational);
    }

    @Override
    public void delete(OperationalEntity operational) {

        operationalRepository.delete(operational);
    }

    /**
     * Checks if the OperationalEntity is valid.
     * 
     * @param   operational     an OperationalEntity to be validated
     * @return                  true if valid, false if invalid
     * @see                     boolean
     */
    private boolean validateData(OperationalEntity operational)
    {
        if (!operational.check())
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}

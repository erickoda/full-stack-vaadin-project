package com.example.demo.backend.client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService implements CrudListener<ClientEntity> {
    private final ClientRepository clientRepository;
    
    /**
     * Function returns a Collection of all ClientEntity objects stored in the database.
     * 
     * @return  Collection<ClientEntity>   a Collection of ClientEntity
     * @see     Collection<ClientEntity>
    */
    @Override
    public Collection<ClientEntity> findAll() {
        return clientRepository.findAll();
    }

    /**
     * Function saves a ClientEntity into the database.
     * 
     * @param   client         a ClientEntity to be added to the database
     * @return  ClientEntity   The ClientEntity saved in the database
     * @see     ClientEntity
    */
    @Override
    public ClientEntity add(ClientEntity client) {
        return clientRepository.save(client);
    }

    /**
     * Function updates a ClientEntity into the database.
     * 
     * @param   client         a ClientEntity to be updated to the database
     * @return  ClientEntity   The ClientEntity updated in the database
     * @see     ClientEntity
    */
    @Override
    public ClientEntity update(ClientEntity client) {
        return clientRepository.save(client);
    }

    /**
     * Function deletes a ClientEntity into the database.
     * 
     * @param   client         a ClientEntity to be deleted to the database
     * @return  ClientEntity   The ClientEntity deleted
     * @see     ClientEntity
    */
    @Override
    public void delete(ClientEntity client) {
        clientRepository.delete(client);
    }

    /**
     * Function returns a List of all cpfs clients stored stored in the database.
     * 
     * @return  ClientEntity   The ClientEntity that has the cpf
     * @see     ClientEntity
    */
    public ArrayList<String> getAllCpfs() {
        ArrayList<String> cpfs = new ArrayList<>();
        Collection<ClientEntity> clients = clientRepository.findAll();

        for (var client: clients) {
            cpfs.add(client.getCpf());
        }

        return cpfs;
    }

    /**
     * Function returns the percentage of clients
     * with age between minAge and maxAge.
     * 
     * @param   minAge    a Integer that represents the minimal chosen age 
     * @param   maxAge    a Integer that represents the maximal chosen age
     * @return  float     a float that represents the percentage of clients that have age between minAge and maxAge
     * @see     float
    */
    public float percentageOfClientsWithAgeBetween(int minAge, int maxAge) {
        Collection<ClientEntity> clients = clientRepository.findAll();
        int totalClients = clients.size();
        int clientsWithAgeBetween = 0;

        for (var client: clients) {
            LocalDate today = LocalDate.now();
            int actualYear = today.getYear();
            int age = actualYear - client.getBirthDate().getYear();
            if (age >= minAge && age < maxAge) {
                clientsWithAgeBetween++;
            }
        }

        return (float) clientsWithAgeBetween / totalClients;
    }
}

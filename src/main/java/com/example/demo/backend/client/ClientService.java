package com.example.demo.backend.client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService implements CrudListener<ClientEntity> {
    private final ClientRepository clientRepository;
    
    @Override
    public Collection<ClientEntity> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public ClientEntity add(ClientEntity client) {
        return clientRepository.save(client);
    }

    @Override
    public ClientEntity update(ClientEntity client) {
        return clientRepository.save(client);
    }

    @Override
    public void delete(ClientEntity client) {
        clientRepository.delete(client);
    }

    public ArrayList<String> getAllCpfs() {
        ArrayList<String> cpfs = new ArrayList<>();
        Collection<ClientEntity> clients = clientRepository.findAll();

        for (var client: clients) {
            cpfs.add(client.getCpf());
        }

        return cpfs;
    }

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

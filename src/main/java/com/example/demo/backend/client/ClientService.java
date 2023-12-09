package com.example.demo.backend.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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
}

package com.example.demo.backend.client;

import java.util.Collection;

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
}

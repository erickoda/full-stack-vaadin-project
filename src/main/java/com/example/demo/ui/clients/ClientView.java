package com.example.demo.ui.clients;

import java.time.LocalDate;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.demo.backend.client.ClientEntity;
import com.example.demo.backend.client.ClientService;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import jakarta.annotation.security.PermitAll;


@Route(value = "clients", layout = MainLayout.class)
@PermitAll
public class ClientView extends VerticalLayout {

    public ClientView(ClientService clientService) {

        var crud = new GridCrud<>(ClientEntity.class, clientService);
        crud.getGrid().setColumns("cpf", "name", "birthDate", "email", "mobileNumber");
        crud.getCrudFormFactory().setVisibleProperties("cpf", "name", "birthDate", "email", "mobileNumber");
        crud.setAddOperationVisible(false);
        crud.setDeleteOperationVisible(false);
        crud.getCrudLayout().addToolbarComponent(new RouterLink("New Client", NewClient.class));
        // crud.getCrudLayout().addToolbarComponent(new RouterLink("Delete Client", DeleteVehicle.class));

        H1 Title = new H1("Clients");
        

        add(
            Title,
            crud,
            new Button("Generate Clients", (event) -> {
                generateClients(clientService);
            })
        );
    }

    public void generateClients(ClientService clientService) {
        for(int i = 0; i < 10; i++) {
            ClientEntity client = new ClientEntity();
            client.setCpf("0000000000" + i);
            client.setName("Client " + i);
            client.setBirthDate(LocalDate.of(1990 + i, 01 + i, 02 + i));
            client.setEmail("client" + i + "@gmail.com");
            client.setMobileNumber("0000000000" + i);
            clientService.add(client);
        }
    }
}

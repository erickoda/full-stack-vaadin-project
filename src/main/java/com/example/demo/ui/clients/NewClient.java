package com.example.demo.ui.clients;


import com.example.demo.backend.client.ClientEntity;
import com.example.demo.backend.client.ClientService;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "clients_new", layout = MainLayout.class)
@PermitAll
public class NewClient extends VerticalLayout {

    TextField cpf = new TextField("CPF");
    TextField name = new TextField("Name");
    DatePicker birthDate = new DatePicker("Birth Date");
    EmailField email = new EmailField("Email"); 
    TextField mobileNumber = new TextField("Mobile Number");
    boolean clientExists;

    /**
     * Constructor to the UI of the client page
     * 
     * @param clientService
    */
    public NewClient(ClientService clientService) {
        H1 Title = new H1("New Client");

        var binder = new Binder<>(ClientEntity.class);
        binder.bindInstanceFields(this);

        FormLayout ClientForms = new FormLayout(cpf, name, birthDate, email, mobileNumber);
        Button SaveButton = new Button("Save", event -> {
            var client = new ClientEntity();
            binder.writeBeanIfValid(client);

            client.setCpf(client.getCpf().replaceAll(" ", ""));
            client.setName(client.getName().replaceAll(" ", ""));
            client.setEmail(client.getEmail().replaceAll(" ", ""));
            client.setMobileNumber(client.getMobileNumber().replaceAll(" ", ""));
            clientExists = false;

            if(clientIsValid(clientService, client) == false) return;

            if (client.getCpf().isEmpty() || client.getName().isEmpty() || client.getEmail().isEmpty() || client.getMobileNumber().isEmpty()) {
                Notification.show("Please fill all the fields");
            } else {
                clientService.add(client);
                Notification.show("Client saved");
                UI.getCurrent().navigate(ClientView.class);
            }
        });

        add(
            Title,
            ClientForms,
            SaveButton
        );
        
    }

    /**
     * Function to verify if the a client exists
     * 
     * @param clientService
     * @param client
     */
    public void verifyClient(ClientService clientService, ClientEntity client) {
        clientService.findAll().forEach(clientEntity -> {
            if (client.getCpf().equals(clientEntity.getCpf())) {
                clientExists = true;
            }
        });
    }

    /**
     * Function to test if a given new ClientEntity is valid by testing if it already exists
     * 
     * @param clientService
     * @param client
     * @return true if it does not exist and is valid, false otherwise
     */
    public boolean clientIsValid(ClientService clientService, ClientEntity client) {
        verifyClient(clientService, client);

        if (clientExists == true) {
            return false;
        }

        return true;
    }
}
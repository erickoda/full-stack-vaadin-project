package com.example.demo.ui.rent;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.demo.backend.client.ClientService;
import com.example.demo.backend.rent.RentEntity;
import com.example.demo.backend.rent.RentService;
import com.example.demo.backend.rent.RentStatus;
import com.example.demo.ui.MainLayout;
import com.lowagie.text.Paragraph;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
    
@Route(value = "Pick Vehicle", layout = MainLayout.class)
@PageTitle("Pick Vehicle")
@PermitAll
public class PickVehicle extends VerticalLayout {

    // Labels For Input
    ComboBox<String> cpf    = new ComboBox<String>("CPF");
    ComboBox<Integer> id    = new ComboBox<Integer>("Id");
    Button confirm          = new Button("Confirm");
    Button back             = new Button("Back");


    // Layout for Input
    HorizontalLayout inputs     = new HorizontalLayout();
    
    // Layout for Output 
    HorizontalLayout rentInfo   = new HorizontalLayout();

    // Titles for labels on output
    H4 RentIdTitle          = new H4("Id");
    H4 UserCpfTitle         = new H4("CPF");
    H4 RentTakeOutDateTitle = new H4("Take Out");
    H4 RentReturnDateTitle  = new H4("Return");
    H4 RentValueTitle       = new H4("Value");

    VerticalLayout IdLable      = new VerticalLayout();
    VerticalLayout CpfLable     = new VerticalLayout();
    VerticalLayout TakeLable    = new VerticalLayout();
    VerticalLayout ReturnLable  = new VerticalLayout();
    VerticalLayout ValueLable   = new VerticalLayout();

    Paragraph info = new Paragraph();

    
    ArrayList<RentEntity> rents = new ArrayList<RentEntity>();
    /**
     * Constructor to vehicle selector on web application
     * 
     * @param rentService
     * @param clientService
     */
    public PickVehicle(RentService rentService, ClientService clientService) {

        cpf.setItems(clientService.getAllCpfs());
        id.setItems(rentService.getAllIds());
        

        cpf.addValueChangeListener(event -> {
            ArrayList<Integer> availableRents = new ArrayList<Integer>();

            // SELECT * FROM Rent WHERE cpf = cpf.input AND data.IsNow() AND Status = ACTIVE
            rentService
                    .findAll()
                    .stream()
                    .filter(rent -> rent.getStatus().equals(RentStatus.ACTIVE))
                    .filter(rent -> rent.getCpf().equals(cpf.getValue()))
                    .filter(rent -> rent.getTakeOutDate().equals(LocalDate.now()))
                    .forEach(rent -> {
                        RentVehicles(rent);
                        availableRents.add(rent.getId());
                    });

            id.setItems(availableRents);
            
            if (availableRents.isEmpty()) {
                rentInfo.removeAll();
                info = new Paragraph("No Reserves for this CPF");
                rentInfo.add(info.getContent());
                return;
            }
        });


        // // Still Not Add on screen, but its for be the first (title) on Vertical Layout
        // IdLable.add(RentIdTitle);
        // CpfLable.add(UserCpfTitle);
        // TakeLable.add(RentTakeOutDateTitle);
        // ReturnLable.add(RentReturnDateTitle);
        // ValueLable.add(RentValueTitle);

        confirm.addClickListener(event -> {
            int chooseId = id.getValue();
            RentEntity chooseRent = null;

            // getting rent by id
            for (RentEntity rent : rents) {
                if (rent.getId() == chooseId) {
                    chooseRent = rent;
                    break;
                }
            }

            if (chooseRent == null) return;

            chooseRent.setStatus(RentStatus.EFFECTED);
            rentService.update(chooseRent);
            Notification.show("Vehicle successfully taken");
        });

        back.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("rentss"));
        });

        
        inputs.add(cpf, id, confirm, back);
        inputs.setAlignItems(Alignment.END);

        add(
                new H1("Pick Vehicle"),
                inputs,
                rentInfo);
    }

    /**
     * Function to update the text in the vehicle selection page of the web application
     * 
     * @param rent
     */
    public void RentVehicles(RentEntity rent) {
        // Messages That will be generated for All rent that pass the filters
        rentInfo.removeAll();
        info = new Paragraph("");
        rents.add(rent);
        
        // Adding the values under the titlerents
        
        info = new Paragraph(Integer.toString(rent.getId()));
        IdLable.removeAll();
        IdLable.add(RentIdTitle);
        IdLable.add(info.getContent());
        IdLable.add(new HtmlComponent("br"));
        rentInfo.add(IdLable);
        
        info = new Paragraph(rent.getCpf());
        CpfLable.removeAll();
        CpfLable.add(UserCpfTitle);
        CpfLable.add(info.getContent());
        CpfLable.add(new HtmlComponent("br"));
        rentInfo.add(CpfLable);
        
        info = new Paragraph(rent.getTakeOutDate().toString());
        TakeLable.removeAll();
        TakeLable.add(RentTakeOutDateTitle);
        TakeLable.add(info.getContent());
        TakeLable.add(new HtmlComponent("br"));
        rentInfo.add(TakeLable);
        
        info = new Paragraph(rent.getReturnDate().toString());
        ReturnLable.removeAll();
        ReturnLable.add(RentReturnDateTitle);
        ReturnLable.add(info.getContent());
        ReturnLable.add(new HtmlComponent("br"));
        rentInfo.add(ReturnLable);
        
        info = new Paragraph(Integer.toString(rent.getRentValue()));
        ValueLable.removeAll();
        ValueLable.add(RentValueTitle);
        ValueLable.add("R$" + info.getContent());
        ValueLable.add(new HtmlComponent("br"));
        rentInfo.add(ValueLable);

    }
}

package com.example.demo.ui.rent;

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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "Cancel Rent", layout = MainLayout.class)
@PageTitle("Cancel Rent")
@PermitAll
public class CancelRent extends  VerticalLayout {
    // Labels For Input
    ComboBox<Integer> id    = new ComboBox<Integer>("Id");
    ComboBox<String> cpf    = new ComboBox<String>("CPF");
    Button confirm          = new Button("Confirm");
    Button back             = new Button("Back");

    // Layout for Input
    HorizontalLayout inputs     = new HorizontalLayout();

    // Layout for Output
    HorizontalLayout rentInfo   = new HorizontalLayout();

    HorizontalLayout emptyLayout = new HorizontalLayout();

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
    ArrayList<RentEntity> rents = new ArrayList<RentEntity>();

    Paragraph empty = new Paragraph("No Reserves for this CPF");

    /**
     * Constructor to the web page to cancel a Rent
     * 
     * @param rentService
     * @param clientService
     */
    public CancelRent(RentService rentService, ClientService clientService) {

        cpf.setItems(clientService.getAllCpfs());
        id.setItems(rentService.getAllIds());
        
        cpf.addValueChangeListener(event -> {

            // Cleaning the screen
            IdLable.removeAll();
            CpfLable.removeAll();
            TakeLable.removeAll();
            ReturnLable.removeAll();
            ValueLable.removeAll();
            // rentInfo.removeAll();
            emptyLayout.removeAll();
            rents.clear();

            // SELECT * FROM Rent WHERE cpf = cpf.input AND data.IsNow() AND (Status = ACTIVE OR Status = EFFECTED)
            rentService
                .findAll()
                .stream()
                .filter(rent -> rent.getStatus().equals(RentStatus.ACTIVE) || rent.getStatus().equals(RentStatus.EFFECTED))
                .filter(rent -> rent.getCpf().equals(cpf.getValue()))
                .forEach(rent -> RentVehicles(rent));
            
            if (rents.isEmpty()) {
                emptyLayout.add(empty.getContent());
                return;
            } else {
                emptyLayout.removeAll();
            }
        });

        // Still Not Add on screen, but its for be the first (title) on Vertical Layout
        IdLable.add(RentIdTitle);
        CpfLable.add(UserCpfTitle);
        TakeLable.add(RentTakeOutDateTitle);
        ReturnLable.add(RentReturnDateTitle);
        ValueLable.add(RentValueTitle);

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

            cpf.clear();
            id.clear();

            chooseRent.setStatus(RentStatus.CANCELLED);
            rentService.update(chooseRent);
        });

        back.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("rentss"));
        });

        
        
        inputs.add(cpf, id, confirm, back);
        inputs.setAlignItems(Alignment.END);

        add(
            new H1("Cancel Rental"),
            inputs,
            rentInfo,
            emptyLayout
        );
    }

    /**
     * Function to set the text of the rent cancel web page
     * 
     * @param rent
     */
    public void RentVehicles(RentEntity rent) {
        // Messages That will be generated for All rent that pass the filters
        Paragraph RentIdParagraph           = new Paragraph(Integer.toString(rent.getId()));
        Paragraph UserCpfParagraph          = new Paragraph(rent.getCpf());
        Paragraph RentTakeOutDateParagraph  = new Paragraph(rent.getTakeOutDate().toString());
        Paragraph RentReturnDateParagraph   = new Paragraph(rent.getReturnDate().toString());
        Paragraph RentValueParagraph        = new Paragraph(Integer.toString(rent.getRentValue()));
        rents.add(rent);

        // Adding the values under the title

        IdLable.add(RentIdParagraph.getContent());
        IdLable.add(new HtmlComponent("br"));
        rentInfo.add(IdLable);

        CpfLable.add(UserCpfParagraph.getContent());
        CpfLable.add(new HtmlComponent("br"));
        rentInfo.add(CpfLable);

        TakeLable.add(RentTakeOutDateParagraph.getContent());
        TakeLable.add(new HtmlComponent("br"));
        rentInfo.add(TakeLable);

        ReturnLable.add(RentReturnDateParagraph.getContent());
        ReturnLable.add(new HtmlComponent("br"));
        rentInfo.add(ReturnLable);

        ValueLable.add("R$" + RentValueParagraph.getContent());
        ValueLable.add(new HtmlComponent("br"));
        rentInfo.add(ValueLable);

    }
}

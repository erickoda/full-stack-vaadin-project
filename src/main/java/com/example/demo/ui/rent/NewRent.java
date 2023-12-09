package com.example.demo.ui.rent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Back;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Horizontal;

import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.data.binder.Binder;

import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;

import com.example.demo.backend.client.ClientService;
import com.example.demo.backend.rent.RentEntity;
import com.example.demo.backend.rent.RentService;
import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.backend.vehicle.VehicleService;
import com.example.demo.backend.vehicle.VehicleTier;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.notification.Notification;


@Route(value = "new_rents", layout = MainLayout.class)
@PermitAll

public class NewRent extends VerticalLayout {

    String licensePlate = new String();
    // TextField cpf = new TextField("CPF");
    ComboBox<String> cpf = new ComboBox<String>("CPF");
    ComboBox<String> licensePlateComboBox = new ComboBox<String>("License Plate");
    DatePicker takeOutDate = new DatePicker("Take Out Date");
    DatePicker returnDate = new DatePicker("Return Date");
    IntegerField rentValue = new IntegerField("Rent Value");
    Checkbox cleanInterior = new Checkbox();    
    Checkbox cleanExterior = new Checkbox();
    Checkbox hasInsurance = new Checkbox();

    Grid<VehicleEntity> vehicleGrid = new Grid<VehicleEntity>(VehicleEntity.class, false);
    ComboBox<VehicleTier> vehicleTiersComboBox = new ComboBox<VehicleTier>("Vehicle Tier");

    IntegerField yearOfFabrication = new IntegerField();


    public NewRent(RentService rentService, VehicleService vehicleService, ClientService clientService){
        H1 Title = new H1("New Rent");

        var binder = new Binder<>(RentEntity.class);
        binder.bindInstanceFields(this);
        
        rentValue.isReadOnly();

        cpf.setItems(clientService.getAllCpfs());

        FormLayout RentForms = new FormLayout(takeOutDate, returnDate, cpf, rentValue/*, cleanInterior, cleanExterior, hasInsurance*/);
        Button SaveButton = new Button("Save", event -> {
            var rent = new RentEntity();
            binder.writeBeanIfValid(rent);

            rent.setLicensePlate(licensePlate);

            rent.setTakeOutDate(takeOutDate.getValue());
            rent.setReturnDate(returnDate.getValue());

            rent.setCpf(rent.getCpf().replaceAll(" ", ""));
            rent.setCpf(rent.getCpf().replaceAll(".", ""));
            rent.setCpf(rent.getCpf().replaceAll("-", ""));

            rent.setHasInsurance(hasInsurance.getValue());
            rent.setCleanExterior(cleanExterior.getValue());
            rent.setCleanInterior(cleanInterior.getValue());

            rent.setRentValue(rentValue.getValue());

            // Do validation of rent just like isVehiclesFieldsValid

            rentService.add(rent);
            Notification.show("Rent added successfully");
            binder.readBean(new RentEntity());
        });

        Button BackButtons = new Button("Back", event -> {
            getUI().ifPresent(ui -> ui.navigate("rentss"));
        });

        HorizontalLayout Buttons = new HorizontalLayout(BackButtons, SaveButton);

        vehicleGrid.addColumn(VehicleEntity::getLicensePlate).setHeader("License Plate");
        vehicleGrid.addColumn(VehicleEntity::getBuilder).setHeader("Builder");
        vehicleGrid.addColumn(VehicleEntity::getModel).setHeader("Model");
        vehicleGrid.addColumn(VehicleEntity::getColour).setHeader("Colour");
        vehicleGrid.addColumn(VehicleEntity::getYearOfFabrication).setHeader("Year of Fabrication");
        vehicleGrid.addColumn(VehicleEntity::getTier).setHeader("Tier");
        vehicleGrid.addColumn(VehicleEntity::getStatus).setHeader("Status");

        vehicleTiersComboBox.addValueChangeListener(event -> {
            vehicleGrid.setItems(vehicleService.getVehicle(rentService.findUnrentedVehicles(takeOutDate.getValue(), returnDate.getValue()), vehicleTiersComboBox.getValue()));
            RentForms.add(vehicleGrid);
        });
        
        takeOutDate.addValueChangeListener(event -> {
            if(returnDate.getValue() == null){
                RentForms.remove(licensePlateComboBox);
                RentForms.remove(vehicleTiersComboBox);
                RentForms.remove(vehicleGrid);
                return;
            }
            if(takeOutDate.getValue().isAfter(returnDate.getValue())){
                Notification.show("Return date needs to be after take out date");
                return;
            }
            
            RentForms.add(vehicleTiersComboBox);
            vehicleTiersComboBox.setItems(VehicleTier.values());

            RentForms.add(licensePlateComboBox);
            licensePlateComboBox.setItems(rentService.getUnrentedVehiclesPlates(vehicleService.getVehicle(rentService.findUnrentedVehicles(takeOutDate.getValue(), returnDate.getValue()), vehicleTiersComboBox.getValue())));
        });

        returnDate.addValueChangeListener(event -> {
            if(takeOutDate.getValue() == null){
                RentForms.remove(licensePlateComboBox);
                RentForms.remove(vehicleTiersComboBox);
                RentForms.remove(vehicleGrid);
                return;
            }

            if(takeOutDate.getValue().isAfter(returnDate.getValue())){
                Notification.show("Return date needs to be after take out date");
                return;
            }
            
            RentForms.add(vehicleTiersComboBox);
            vehicleTiersComboBox.setItems(VehicleTier.values());
            
            RentForms.add(licensePlateComboBox);
            licensePlateComboBox.setItems(rentService.getUnrentedVehiclesPlates(vehicleService.getVehicle(rentService.findUnrentedVehicles(takeOutDate.getValue(), returnDate.getValue()), vehicleTiersComboBox.getValue())));
            
        });
        
        licensePlateComboBox.addValueChangeListener(event -> {
            cleanInterior.setLabel("Add Interior Cleaning");
            cleanExterior.setLabel("Add Exterior Cleaning");
            hasInsurance.setLabel("Add Insurance");
            
            RentForms.add(
                cleanInterior,
                cleanExterior,
                hasInsurance
            );

            rentValue.setValue((int) rentService.calculateRentPrice(cleanInterior.getValue(), cleanExterior.getValue(), hasInsurance.getValue(), licensePlate, takeOutDate.getValue(), returnDate.getValue()));
            }
        );

        cleanInterior.addValueChangeListener(event -> {
                cleanInterior.setValue(
                    (Boolean) event.getValue()
                );
                rentValue.setValue((int) rentService.calculateRentPrice(cleanInterior.getValue(), cleanExterior.getValue(), hasInsurance.getValue(), licensePlate, takeOutDate.getValue(), returnDate.getValue()));
            }
        );

        cleanExterior.addValueChangeListener(event -> {
                cleanExterior.setValue(
                    (Boolean) event.getValue()
                );
                rentValue.setValue((int) rentService.calculateRentPrice(cleanInterior.getValue(), cleanExterior.getValue(), hasInsurance.getValue(), licensePlate, takeOutDate.getValue(), returnDate.getValue()));
            }
        );

        hasInsurance.addValueChangeListener(event -> {
                hasInsurance.setValue(
                    (Boolean) event.getValue()
                );
                rentValue.setValue((int) rentService.calculateRentPrice(cleanInterior.getValue(), cleanExterior.getValue(), hasInsurance.getValue(), licensePlate, takeOutDate.getValue(), returnDate.getValue()));
            }
        );
        
        add(
            Title, 
            RentForms,
            Buttons
        );
    }

    public static boolean isVehiclesFieldsValid(VehicleEntity vehicle) {

        if (vehicle.getLicensePlate().length() != 7) {
            Notification.show("License Plate must have 7 characters.");
            return false;
        }

        for (int i = 0; i < vehicle.getLicensePlate().length(); i++) {
            if (i < 3) {
                if (Character.isDigit(vehicle.getLicensePlate().charAt(i)) == true) {
                    Notification.show("License Plate must have 3 letters in the first 3 characters.");
                    return false;
                }
            }
            else if (i == 4) {
                if (Character.isDigit(vehicle.getLicensePlate().charAt(i)) == true) {
                    Notification.show("License Plate must have a letter in the 5th character.");
                    return false;
                }
            } else {
                if (Character.isDigit(vehicle.getLicensePlate().charAt(i)) == false) {
                    Notification.show("License Plate must have a digit in the position" + (i + 1) + ".");
                    return false;
                }
            }
        }
        return true;
    }
}

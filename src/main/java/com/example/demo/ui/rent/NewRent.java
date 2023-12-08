package com.example.demo.ui.rent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

import com.example.demo.backend.rent.RentEntity;
import com.example.demo.backend.rent.RentService;
import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.notification.Notification;


@Route(value = "rents", layout = MainLayout.class)
@PermitAll

public class NewRent extends VerticalLayout {

    String licensePlate = new String();
    // int rentValue = 0;
    TextField cpf = new TextField("CPF");
    ComboBox<String> licensePlateComboBox = new ComboBox<String>("License Plate");
    DatePicker takeOutDate = new DatePicker("Take Out Date");
    DatePicker returnDate = new DatePicker("Return Date");
    IntegerField rentValue = new IntegerField("Rent Value");
    Checkbox cleanInterior = new Checkbox();    
    Checkbox cleanExterior = new Checkbox();
    Checkbox hasInsurance = new Checkbox();


    public NewRent(RentService rentService){
        H1 Title = new H1("New Rent");

        var binder = new Binder<>(RentEntity.class);
        binder.bindInstanceFields(this);
        
        rentValue.isReadOnly();
        FormLayout RentForms = new FormLayout(takeOutDate, returnDate, cpf, rentValue/*, cleanInterior, cleanExterior, hasInsurance*/);
        Button SaveButton = new Button("Save", event -> {
            var rent = new RentEntity();
            binder.writeBeanIfValid(rent);

            rent.setCpf(rent.getCpf().replaceAll(" ", ""));
            rent.setCpf(rent.getCpf().replaceAll(".", ""));
            rent.setCpf(rent.getCpf().replaceAll("-", ""));

        });
        
        takeOutDate.addValueChangeListener(event -> {
            if(returnDate.getValue() == null){
                RentForms.remove(licensePlateComboBox);
                return;
            }
            if(takeOutDate.getValue().isAfter(returnDate.getValue())){
                Notification.show("Return date needs to be after take out date");
                return;
            }
            
            RentForms.add(licensePlateComboBox);
            licensePlateComboBox.setItems(rentService.getUnrentedVehiclesPlates(rentService.findUnrentedVehicles(takeOutDate.getValue(), returnDate.getValue())));

        });

        returnDate.addValueChangeListener(event -> {
            if(takeOutDate.getValue() == null){
                RentForms.remove(licensePlateComboBox);
                return;
            }

            if(takeOutDate.getValue().isAfter(returnDate.getValue())){
                Notification.show("Return date needs to be after take out date");
                return;
            }
            
            RentForms.add(licensePlateComboBox);
            licensePlateComboBox.setItems(rentService.getUnrentedVehiclesPlates(rentService.findUnrentedVehicles(takeOutDate.getValue(), returnDate.getValue())));
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
        
        add(Title, 
            RentForms,
            SaveButton
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

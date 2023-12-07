package com.example.demo.ui.rent;

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
import com.example.demo.backend.rent.RentEntity;
import com.example.demo.backend.rent.RentService;
import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.notification.Notification;


@Route(value = "rents", layout = MainLayout.class)
@PermitAll

public class NewRent extends VerticalLayout {

    TextField cpf = new TextField("CPF");
    TextField licensePlate = new TextField("License Plate");
    DatePicker takeOutDate = new DatePicker("Take Out Date");
    DatePicker returnDate = new DatePicker("Return Date");
    IntegerField rentValue = new IntegerField("Rent Value");

    public NewRent(RentService rentService){
        H1 Title = new H1("New Rent");

        var binder = new Binder<>(RentEntity.class);
        binder.bindInstanceFields(this);
        
        FormLayout RentForms = new FormLayout(takeOutDate, returnDate, cpf, rentValue, licensePlate);
        
        add(Title, RentForms);
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
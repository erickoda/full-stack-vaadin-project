package com.example.demo.ui.cars;

import com.example.demo.backend.cars.CarsEntity;
import com.example.demo.backend.cars.CarsService;
import com.example.demo.backend.cars.CarsStatus;
import com.example.demo.backend.cars.CarsTier;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "new_car", layout = MainLayout.class)
@RolesAllowed("GERENTE")
public class NewCar extends VerticalLayout {

    private TextField licensePlate = new TextField("License Plate");
    private TextField builder = new TextField("Builder");
    private TextField model = new TextField("Model");
    private TextField colour = new TextField("Colour");
    private IntegerField yearOfFabrication = new IntegerField("Ano de Fabricação");
    private ComboBox<CarsTier> tier = new ComboBox<CarsTier>("Tier");
    private ComboBox<CarsStatus> status = new ComboBox<CarsStatus>("Status");
    
    public NewCar(CarsService carsService) {

        status.setItems(CarsStatus.values());
        tier.setItems(CarsTier.values());

        var binder = new Binder<>(CarsEntity.class);
        binder.bindInstanceFields(this);
        
        add(
            new H1("New Car"),
            new FormLayout(licensePlate, builder, model, colour, yearOfFabrication, tier, status),
            new Button("Save", event -> {
                var car = new CarsEntity();
                binder.writeBeanIfValid(car);

                car.setLicensePlate(car.getLicensePlate().replaceAll(" ", ""));

                if (isCarsFieldsValid(car) == false) {
                    return;
                }

                carsService.add(car);
                Notification.show("Car added successfully.");
                binder.readBean(new CarsEntity());
            })
        );
    }

    public static boolean isCarsFieldsValid(CarsEntity car) {

        if (car.getLicensePlate().length() != 7) {
            Notification.show("License Plate must have 7 characters.");
            return false;
        }

        for (int i = 0; i < car.getLicensePlate().length(); i++) {
            if (i < 3) {
                if (Character.isDigit(car.getLicensePlate().charAt(i)) == true) {
                    Notification.show("License Plate must have 3 letters in the first 3 characters.");
                    return false;
                }
            } 
            else if (i == 4) {
                if (Character.isDigit(car.getLicensePlate().charAt(i)) == true) {
                    Notification.show("License Plate must have a letter in the 5th character.");
                    return false;
                }
            } else {
                if (Character.isDigit(car.getLicensePlate().charAt(i)) == false) {
                    Notification.show("License Plate must have a digit in the position" + (i + 1) + ".");
                    return false;
                }
            }
        }

        if (car.getYearOfFabrication() < 2010) {
            Notification.show("Year of Fabrication must be greater than 2010.");
            return false;
        }

        return true;
    }
}

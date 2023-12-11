package com.example.demo.ui.vehicle;

import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.backend.vehicle.VehicleService;
import com.example.demo.backend.vehicle.VehicleStatus;
import com.example.demo.backend.vehicle.VehicleTier;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "new_vehicle", layout = MainLayout.class)
@RolesAllowed("GERENTE")
public class NewVehicle extends VerticalLayout {

    private TextField licensePlate = new TextField("License Plate");
    private TextField builder = new TextField("Builder");
    private TextField model = new TextField("Model");
    private TextField colour = new TextField("Colour");
    private IntegerField yearOfFabrication = new IntegerField("Ano de Fabricação");
    private ComboBox<VehicleTier> tier = new ComboBox<VehicleTier>("Tier");
    private ComboBox<VehicleStatus> status = new ComboBox<VehicleStatus>("Status");
    private TextField reasonToDelete = new TextField("Reason To Delete");
    

    /**
    * Constructor to the New Vehicle Layout of web application
    * 
    * @param    vehiclesService   an VehicleService object
    */
    public NewVehicle(VehicleService vehiclesService) {

        H1 Title;
        FormLayout VehicleForms;
        Button BackButton;
        Button SaveButton;
        HorizontalLayout Buttons;

        status.setItems(VehicleStatus.values());
        tier.setItems(VehicleTier.values());

        var binder = new Binder<>(VehicleEntity.class);
        binder.bindInstanceFields(this);

        Title = new H1("New vehicle");
        VehicleForms = new FormLayout(licensePlate, builder, model, colour, yearOfFabrication, tier, status);
        BackButton = new Button("Back", event -> {
            UI.getCurrent().navigate(MainVehicleView.class);
        });
        SaveButton = new Button("Save", event -> {
            var vehicle = new VehicleEntity();
            binder.writeBeanIfValid(vehicle);

            vehicle.setLicensePlate(vehicle.getLicensePlate().replaceAll(" ", ""));

            if (isVehiclesFieldsValid(vehicle) == false) {
                return;
            }

            vehiclesService.add(vehicle);
            Notification.show("Vehicle added successfully.");
            binder.readBean(new VehicleEntity());
        });

        Buttons = new HorizontalLayout();
        Buttons.setAlignItems(FlexComponent.Alignment.CENTER);
        Buttons.add(BackButton);
        Buttons.add(SaveButton);

        status.addValueChangeListener(event -> {
            if (status.getValue().toString().equals("DELETADO")) {
                VehicleForms.add(reasonToDelete);
            } else {
                VehicleForms.remove(reasonToDelete);
            }
        });

        add(
            Title,
            VehicleForms,
            Buttons
        );
    }

    /**
     * Verify if the user's inputs are valid to create a new Vehicle
     * 
     * @param   vehicle    a Vehicle Entity created by the user
     * @return             true if the user's inputs are valid, false otherwise
     * @see                boolean
     */
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

        if (vehicle.getYearOfFabrication() < 2010) {
            Notification.show("Year of Fabrication must be greater than 2010.");
            return false;
        }

        return true;
    }
}

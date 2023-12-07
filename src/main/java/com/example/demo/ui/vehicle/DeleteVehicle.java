package com.example.demo.ui.vehicle;

import java.util.Collection;
import java.util.List;

import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.backend.vehicle.VehicleService;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Horizontal;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "delete_vehicle", layout = MainLayout.class)
@RolesAllowed("GERENTE")
public class DeleteVehicle extends VerticalLayout{
    
    ComboBox<String> licensePlate = new ComboBox<String>("License Plate");
    HorizontalLayout Vehicle;

    public DeleteVehicle(VehicleService vehicleService) {

        H1 Title = new H1("Delete Vehicle");
        vehicleService
            .findAll()
            .forEach(vehicle -> licensePlate.setItems(vehicle.getLicensePlate()));

        add (
            Title,
            licensePlate
        );
    }

    public void updateScreen(VehicleService vehicleService) {
        licensePlate.addValueChangeListener(event -> {
            var vehicle = vehicleService.findAll().stream().filter(v -> v.getLicensePlate().equals(event.getValue())).findFirst().get();

            // vehicleService.delete(vehicle);
        });
    }
}

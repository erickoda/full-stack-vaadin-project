package com.example.demo.ui.vehicle;

import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.backend.vehicle.VehicleService;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import org.vaadin.crudui.crud.impl.GridCrud;

import jakarta.annotation.security.RolesAllowed;


@Route(value = "manager", layout = MainLayout.class)
@PageTitle("Gerente")
@RolesAllowed("GERENTE")
public class MainVehicleView extends VerticalLayout {
    
    public MainVehicleView(VehicleService vehicleService) {

        var crud = new GridCrud<>(VehicleEntity.class, vehicleService);
        crud.getGrid().setColumns("licensePlate", "builder", "model", "colour", "yearOfFabrication", "tier", "status");
        crud.getCrudFormFactory().setVisibleProperties("licensePlate", "builder", "model", "colour", "yearOfFabrication", "tier", "status");
        crud.setAddOperationVisible(false);
        crud.setDeleteOperationVisible(false);
        crud.getCrudLayout().addToolbarComponent(new RouterLink("New Vehicle", NewVehicle.class));
        crud.getCrudLayout().addToolbarComponent(new RouterLink("Delete Vehicle", DeleteVehicle.class));

        add(
            new H1("Vehicle"),
            crud
        );
    }
}

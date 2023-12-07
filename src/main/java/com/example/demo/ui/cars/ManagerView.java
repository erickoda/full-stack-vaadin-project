package com.example.demo.ui.cars;

import com.example.demo.backend.cars.CarsEntity;
import com.example.demo.backend.cars.CarsService;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import org.vaadin.crudui.crud.impl.GridCrud;

import jakarta.annotation.security.RolesAllowed;


@Route(value = "manager", layout = MainLayout.class)
@PageTitle("Gerente")
@RolesAllowed("GERENTE")
public class ManagerView extends VerticalLayout {
    
    public ManagerView(CarsService carsService) {

        var crud = new GridCrud<>(CarsEntity.class, carsService);
        crud.getGrid().setColumns("licensePlate", "builder", "model", "colour", "yearOfFabrication", "tier", "status");
        crud.getCrudFormFactory().setVisibleProperties("licensePlate", "builder", "model", "colour", "yearOfFabrication", "tier", "status");
        crud.setAddOperationVisible(false);
        crud.getCrudLayout().addToolbarComponent(new RouterLink("New Car", NewCar.class));

        add(
            new H1("Gerente"),
            crud
        );
    }
    
}

package com.example.demo.ui.rent;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.demo.backend.rent.RentEntity;
import com.example.demo.backend.rent.RentService;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import jakarta.annotation.security.PermitAll;

@Route(value = "rentss", layout = MainLayout.class)
@PermitAll
public class RentsView extends VerticalLayout {
    
    public RentsView(RentService rentService) {

        var crud = new GridCrud<>(RentEntity.class, rentService);
        crud.getGrid().setColumns("id", "cpf", "licensePlate", "takeOutDate", "returnDate", "rentValue", "status", "cleanExterior", "cleanInterior", "hasInsurance");
        crud.getCrudFormFactory().setVisibleProperties("cpf", "licensePlate", "takeOutDate", "returnDate", "rentValue", "status", "cleanExterior", "cleanInterior", "hasInsurance");
        crud.setAddOperationVisible(false);
        crud.setDeleteOperationVisible(false);
        crud.setUpdateOperationVisible(false);
        crud.getCrudLayout().addToolbarComponent(new RouterLink("New Rent", NewRent.class));
        
        add(
            new H1("Rents"),
            crud
        );
    }
}

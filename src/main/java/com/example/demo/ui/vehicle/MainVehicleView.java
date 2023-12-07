package com.example.demo.ui.vehicle;

import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.backend.vehicle.VehicleService;
import com.example.demo.backend.vehicle.VehicleStatus;
import com.example.demo.backend.vehicle.VehicleTier;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
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
        crud.setUpdateOperationVisible(false);
        crud.getCrudLayout().addToolbarComponent(new RouterLink("New Vehicle", NewVehicle.class));
        crud.getCrudLayout().addToolbarComponent(new RouterLink("Delete Vehicle", DeleteVehicle.class));

        add(
            new H1("Vehicle"),
            crud,
            new Button("Gerar Carros", event -> {

                for(int i = 0; i < 10; i++) {
                    VehicleEntity vehicle = new VehicleEntity(); 
                    vehicle.setLicensePlate("AAA" + i + "A" + i + i);
                    vehicle.setBuilder("Fiat");
                    vehicle.setModel("Uno");
                    vehicle.setColour("Black");
                    vehicle.setYearOfFabrication(2013 + 1);

                    if (i < 3) {
                        vehicle.setTier(VehicleTier.BASICO);
                    } else if (i < 6) {
                        vehicle.setTier(VehicleTier.PADRAO);
                    } else {
                        vehicle.setTier(VehicleTier.PREMIUM);
                    }

                    if (i < 6) {
                        vehicle.setStatus(VehicleStatus.DISPONIVEL);
                    } else {
                        vehicle.setStatus(VehicleStatus.INDISPONIVEL);
                    }

                    vehicleService.add(vehicle);
                }
            })
        );
    }
}

package com.example.demo.ui.operational;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.demo.backend.operational.OperationalEntity;
import com.example.demo.backend.operational.OperationalService;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import jakarta.annotation.security.RolesAllowed;


@Route(value = "operational_main", layout = MainLayout.class)
@PageTitle("Operational Main")
@RolesAllowed("GERENTE")
public class OperationMainView extends VerticalLayout {
    
    public OperationMainView(OperationalService operationalService) {

        var crud = new GridCrud<>(OperationalEntity.class, operationalService);
        crud.getGrid().setColumns("tier", "dailyRent", "fuelFillValue", "exteriorCleaningValue", "interiorCleaningValue", "insuranceDailyValue");
        crud.getCrudFormFactory().setVisibleProperties("tier", "dailyRent", "fuelFillValue", "exteriorCleaningValue", "interiorCleaningValue", "insuranceDailyValue");
        crud.setAddOperationVisible(false);
        crud.setDeleteOperationVisible(false);
        crud.setUpdateOperationVisible(false);
        crud.getCrudLayout().addToolbarComponent(new RouterLink("Edit Operational Parameters", EditOperationalView.class));

        add(
            new H1("Operational"),
            crud
        );
    }
}

package com.example.demo.ui.operational;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.demo.backend.operational.OperationalEntity;
import com.example.demo.backend.operational.OperationalService;
import com.example.demo.backend.vehicle.VehicleTier;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
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
    
    /**
     * Constructor to the operational parameter edit web page
     *  
     * @param operationalService
     */
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
            crud,
            new Button ("Generate Operational Parameters", event -> {
                generateOperational(operationalService);
            })
        );
    }

    /**
     * Generates the operational parameters
     * 
     * @param   operationalService    an OperationalService object
     * @return                        void
     * @see                           void
     */
    public void generateOperational(OperationalService operationalService) {
        OperationalEntity operational = new OperationalEntity();
        operational.setTier(VehicleTier.BASICO);
        operational.setDailyRent(100);
        operational.setFuelFillValue(100);
        operational.setExteriorCleaningValue(100);
        operational.setInteriorCleaningValue(100);
        operational.setInsuranceDailyValue(100);

        operationalService.add(operational);

        operational.setTier(VehicleTier.PADRAO);
        operational.setDailyRent(200);
        operational.setFuelFillValue(200);
        operational.setExteriorCleaningValue(200);
        operational.setInteriorCleaningValue(200);
        operational.setInsuranceDailyValue(200);

        operationalService.add(operational);

        operational.setTier(VehicleTier.PREMIUM);
        operational.setDailyRent(300);
        operational.setFuelFillValue(300);
        operational.setExteriorCleaningValue(300);
        operational.setInteriorCleaningValue(300);
        operational.setInsuranceDailyValue(300);

        operationalService.add(operational);
    }
}

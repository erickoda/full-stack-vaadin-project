package com.example.demo.ui.operational;

import com.example.demo.backend.operational.OperationalEntity;
import com.example.demo.backend.operational.OperationalService;

import com.example.demo.backend.vehicle.VehicleTier;
import com.example.demo.ui.MainLayout;
import com.example.demo.ui.vehicle.MainVehicleView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;



@Route(value = "operational", layout = MainLayout.class)
@PageTitle("Operational")
@RolesAllowed("GERENTE")
public class EditOperationalView  extends VerticalLayout {
    private ComboBox<VehicleTier> tier = new ComboBox<VehicleTier>("Tier");
    private IntegerField dailyRent = new IntegerField("Daily Rentability");
    private IntegerField fuelFillValue = new IntegerField("Fuel Fill Value");
    private IntegerField exteriorCleaningValue = new IntegerField("Exterior Cleaning Value");
    private IntegerField interiorCleaningValue = new IntegerField("Interior Cleaning Value");
    private IntegerField insuranceDailyValue = new IntegerField("Insurance Daily Value");

    private static OperationalEntity operationalEnt = null;

    /**
     * Constructor to the operational parameter edit web page
     *  
     * @param operationalService
     */
    public EditOperationalView(OperationalService operationalService) {
        H3 Title;
        FormLayout operationalForms;
        Button BackButton;
        Button SaveButton;
        HorizontalLayout Buttons;

        tier.addValueChangeListener(event -> {
            VehicleTier tierId = tier.getValue();
            
            if (tierId == null) {
                dailyRent.clear();
                fuelFillValue.clear();
                exteriorCleaningValue.clear();
                interiorCleaningValue.clear();
                insuranceDailyValue.clear();
                return;
            }
            
            getOperationalParam(operationalService, tierId);
            
            if (operationalEnt == null) {
                
                dailyRent.clear();
                fuelFillValue.clear();
                exteriorCleaningValue.clear();
                interiorCleaningValue.clear();
                insuranceDailyValue.clear();
                return;
            }

            dailyRent.setValue(operationalEnt.getDailyRent());
            fuelFillValue.setValue(operationalEnt.getFuelFillValue());
            exteriorCleaningValue.setValue(operationalEnt.getExteriorCleaningValue());
            interiorCleaningValue.setValue(operationalEnt.getInteriorCleaningValue());
            insuranceDailyValue.setValue(operationalEnt.getInsuranceDailyValue());
            operationalEnt = null;

        });


        tier.setItems(VehicleTier.values());
        
        var binder = new Binder<>(OperationalEntity.class);
        binder.bindInstanceFields(this);

        Title = new H3("Change Params Operational");
        operationalForms = new FormLayout(tier, dailyRent, fuelFillValue, exteriorCleaningValue, interiorCleaningValue, insuranceDailyValue);

        BackButton = new Button("Back", event -> {
            UI.getCurrent().navigate(MainVehicleView.class);
        });

        SaveButton = new Button("Save", event -> {
            var operational = new OperationalEntity();
            binder.writeBeanIfValid(operational);
            
            operationalService.update(operational);
            Notification.show("Param changed successfully.");
            operational.getDailyRent();
            operational.getFuelFillValue();
            operational.getExteriorCleaningValue();
            operational.getInteriorCleaningValue();
            operational.getInsuranceDailyValue();

        });

        Buttons = new HorizontalLayout();
        Buttons.setAlignItems(FlexComponent.Alignment.CENTER);
        Buttons.add(BackButton);
        Buttons.add(SaveButton);
        
        add (
            new H2("Operational"),
            Title,
            operationalForms,
            Buttons
        );
    }
    
    /**
     * Get Operational Parameters querying with the tier
     * 
     * @param   operationalService    an OperationalService object
     * @param   tier                  a VehicleTier object
     * @return                        void
     * @see                           void
     */
    public static void getOperationalParam(OperationalService operationalService,  VehicleTier tier) {
        operationalService
            .findAll()
            .stream()
            .filter(op -> op.getTier().equals(tier))
            .findFirst()
            .ifPresent(op -> operationalEnt = op);
            
    }
}

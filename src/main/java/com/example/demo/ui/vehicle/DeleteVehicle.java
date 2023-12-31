package com.example.demo.ui.vehicle;

import java.util.ArrayList;

import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.backend.vehicle.VehicleService;
import com.example.demo.backend.vehicle.VehicleStatus;
import com.example.demo.ui.MainLayout;
import com.lowagie.text.Paragraph;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "delete_vehicle", layout = MainLayout.class)
@RolesAllowed("GERENTE")
public class DeleteVehicle extends VerticalLayout{
    
    TextField reasonToDelete = new TextField("Reason To Delete");
    ComboBox<String> LicensePlateComboBox = new ComboBox<String>("License Plate");
    VehicleEntity chosenVehicle;
    HorizontalLayout ChosenVehicleLayout = new HorizontalLayout();
    H2 VehiclePropertiesTitle = new H2("Vehicle Properties");
    H3 VehicleBuilderTitle = new H3("Builder");
    H3 VehicleModelTitle = new H3("Model");
    H3 VehicleColourTitle = new H3("Colour");
    H3 VehicleYearOfFabricationTitle = new H3("Year Of Fabrication");
    H3 VehicleTierTitle = new H3("Tier");
    H3 VehicleStatusTitle = new H3("Status");
    Button DeleteButton = new Button("Delete");

    /**
     * Constructor to the Delete Vehicle Layout of web application
     * 
     * @param    vehicleService   a VehicleService object
    */
    public DeleteVehicle(VehicleService vehicleService) {

        ArrayList<String> vehiclePlatesStrings = new ArrayList<String>();
        ConfirmDialog confirmDelete = new ConfirmDialog();
        
        var binder = new Binder<>(VehicleEntity.class);
        binder.bindInstanceFields(this);

        H1 Title = new H1("Delete Vehicle");
        HorizontalLayout DeleteForms = new HorizontalLayout(LicensePlateComboBox, reasonToDelete, DeleteButton);
        DeleteForms.setAlignItems(Alignment.BASELINE);

        DeleteButton.addClickListener(event -> {

            confirmDelete.setHeader("Confirm Delete");
            confirmDelete.setText(
                "Are you sure you want to delete this vehicle?" +
                "License Plate: " + chosenVehicle.getLicensePlate() + "\n" +
                "Reason To Delete: " + reasonToDelete.getValue()
            );
            confirmDelete.open();
            confirmDelete.addConfirmListener(e -> confirmDelete(vehicleService, binder));
            confirmDelete.addCancelListener(e -> confirmDelete.close());
        });

        vehicleService
            .findAll()
            .forEach(vehicle -> {
                if (vehicle.getStatus() != VehicleStatus.DELETADO)
                    vehiclePlatesStrings.add(vehicle.getLicensePlate());
            });

        LicensePlateComboBox.setItems(vehiclePlatesStrings);

        updateScreen(vehicleService);

        add (
            Title,
            DeleteForms,
            ChosenVehicleLayout
        );
    }

    /**
    * Update the screen when the user selects a License Plate
    * 
    * @param    vehiclesService   a VehicleService object
    * @return                     void
    * @see                        void
    */
    public void updateScreen(VehicleService vehicleService) {
        
        LicensePlateComboBox.addValueChangeListener(event -> {

            getVehicle(vehicleService, LicensePlateComboBox);

            if (chosenVehicle == null) {
                Notification.show("Vehicle not found");
                return;
            }

            showVehicleData();
        });
    }

    /**
    * Update the status of the Vehicle to DELETADO and add the reason to delete
    * 
    * @param    vehiclesService   a VehicleService object
    * @param    binder            a Binder<VehicleEntity> object
    * @return                     void
    * @see                        void
    */
    public void confirmDelete(VehicleService vehicleService, Binder<VehicleEntity> binder) {
        if (chosenVehicle == null) {
            Notification.show("Vehicle not found");
            return;
        }

        if (reasonToDelete.getValue().isEmpty()) {
            Notification.show("Reason to delete is empty");
            return;
        }
        
        binder.writeBeanIfValid(chosenVehicle);
        
        chosenVehicle.setStatus(VehicleStatus.DELETADO);
        chosenVehicle.setReasonToDelete(reasonToDelete.getValue());

        vehicleService.update(chosenVehicle);
        Notification.show("Vehicle Deleted successfully.");
        binder.readBean(new VehicleEntity());
    }

    /**
    * Get the Vehicle querying with the License Plate
    * 
    * @param    vehiclesService   a VehicleService object
    * @param    event             a ComboBox<String> object
    * @return                     void
    * @see                        void
    */
    public void getVehicle(VehicleService vehicleService, ComboBox<String> event) {
        vehicleService
            .findAll()
            .stream()
            .filter(v -> v.getLicensePlate().equals(event.getValue()))
            .findFirst()
            .ifPresent(vehicle -> {
                chosenVehicle = vehicle;
            });
    }

    /**
    * Show the data of the selected Vehicle in the screen
    * 
    * @param
    * @return                     void
    * @see                        void
    */
    public void showVehicleData() {
        Paragraph VehicleBuilderParagraph = new Paragraph(chosenVehicle.getBuilder());
        Paragraph VehicleModelParagraph = new Paragraph(chosenVehicle.getModel());
        Paragraph VehicleColourParagraph = new Paragraph(chosenVehicle.getColour());
        Paragraph VehicleYearOfFabricationParagraph = new Paragraph(chosenVehicle.getYearOfFabrication().toString());
        Paragraph VehicleTierParagraph = new Paragraph(chosenVehicle.getTier().toString());
        Paragraph VehicleStatusParagraph = new Paragraph(chosenVehicle.getStatus().toString());

        ChosenVehicleLayout.removeAll();

        VerticalLayout Builder = new VerticalLayout();
        Builder.add(VehicleBuilderTitle);
        Builder.add(VehicleBuilderParagraph.getContent());
        ChosenVehicleLayout.add(Builder);

        VerticalLayout Model = new VerticalLayout();
        Model.add(VehicleModelTitle);
        Model.add(VehicleModelParagraph.getContent());
        ChosenVehicleLayout.add(Model);

        VerticalLayout Colour = new VerticalLayout();
        Colour.add(VehicleColourTitle);
        Colour.add(VehicleColourParagraph.getContent());
        ChosenVehicleLayout.add(Colour);

        VerticalLayout YearOfFabrication = new VerticalLayout();
        YearOfFabrication.add(VehicleYearOfFabricationTitle);
        YearOfFabrication.add(VehicleYearOfFabricationParagraph.getContent());
        ChosenVehicleLayout.add(YearOfFabrication);

        VerticalLayout Tier = new VerticalLayout();
        Tier.add(VehicleTierTitle);
        Tier.add(VehicleTierParagraph.getContent());
        ChosenVehicleLayout.add(Tier);

        VerticalLayout Status = new VerticalLayout();
        Status.add(VehicleStatusTitle);
        Status.add(VehicleStatusParagraph.getContent());
        ChosenVehicleLayout.add(Status);

        ChosenVehicleLayout.setAlignItems(Alignment.END);
    }
}

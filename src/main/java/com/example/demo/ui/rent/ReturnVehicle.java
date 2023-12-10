package com.example.demo.ui.rent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.demo.backend.operational.OperationalEntity;
import com.example.demo.backend.operational.OperationalService;
import com.example.demo.backend.rent.RentEntity;
import com.example.demo.backend.rent.RentService;
import com.example.demo.backend.rent.RentStatus;
import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.backend.vehicle.VehicleService;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;


@Route(value="Return Vehicle", layout= MainLayout.class)
@PageTitle("Return Vehicle")
@PermitAll
public class ReturnVehicle extends VerticalLayout {
    
    static ComboBox<String> licensePlate        = new ComboBox<String>("License Plate");
    static IntegerField rentValue               = new IntegerField("Rental Price");
    static Button calculate                     = new Button("Calculate");
    static Button payment                       = new Button("Payment");
    
    static Checkbox cleanInterior               = new Checkbox();
    static Checkbox cleanExterior               = new Checkbox();
    static Checkbox hasInsurance                = new Checkbox();
    static RadioButtonGroup<Integer> rb         = new RadioButtonGroup<Integer>();


    static ArrayList<String> plates             = new ArrayList<String>();
    static RentEntity thisRent                  = null;
    static VehicleEntity thisVehicle            = null;
    static OperationalEntity thisOperational    = null;
    public ReturnVehicle(OperationalService operationalService, VehicleService vehicleService, RentService rentService) {
        

        HorizontalLayout fields = new HorizontalLayout();

        fields.setAlignItems(Alignment.END);
        fields.add(licensePlate, rentValue, calculate, payment);
        rentValue.isReadOnly();
        
        getPlate(rentService);
        licensePlate.setItems(plates);
        

        rb.setLabel("Nivel de combustivel");
        rb.setItems(Arrays.asList(0, 25, 50, 75, 100));
        

        HorizontalLayout boxes = new HorizontalLayout();
        
        cleanInterior.setLabel("Add Interior Cleaning");
        cleanExterior.setLabel("Add Exterior Cleaning");
        hasInsurance.setLabel("Add Insurance");

        boxes.add(cleanInterior, cleanExterior, hasInsurance);

        
        add(fields, rb, boxes);

        licensePlate.addValueChangeListener(event -> {
            getVehicle(vehicleService);
            getOperational(operationalService);
            getRent(rentService);
        });

        calculate.addClickListener(event -> {
            if (
                thisRent        == null || 
                thisVehicle     == null ||
                thisOperational == null 
            ) { 
                Notification.show("Need a License Plate");
                return; 
            }

            if (rb.isEmpty()) {
                Notification.show("Need level of tank");
                return;
            }

            calculateValue(rentService, vehicleService, operationalService);
        });

        payment.addClickListener(event -> {
            if (rentValue.isEmpty()) return;

            thisRent.setStatus(RentStatus.FINISHED);
            rentService.update(thisRent);        
            
            Notification.show("Obrigado Pela Preferencia");
        });


    }

    static void calculateValue(
        RentService rentService, 
        VehicleService vehicleService, 
        OperationalService operationalService
    ) {
        // get if need do this or if got it right to do
        boolean interior = thisRent.getCleanInterior() || cleanInterior.getValue();
        boolean exterior = thisRent.getCleanExterior() || cleanExterior.getValue();
        boolean insurance = thisRent.getHasInsurance() || hasInsurance.getValue();

        int value = rentService.calculateRentPrice(
                        vehicleService,
                        operationalService,
                        thisOperational.getTier(),
                        thisRent.getLicensePlate(),
                        thisRent.getTakeOutDate(),
                        thisRent.getReturnDate(),
                        interior,
                        exterior,
                        insurance
                    );
                        //  valor tank [0,1]   * preco pelo tanque cheio 
        int fuel = (100 - rb.getValue()) / 100 * thisOperational.getFuelFillValue();
        
        // value dont got fill fuel price
        int total = fuel + value;

        rentValue.setValue(total);
    }

    static void getOperational(OperationalService operationalService) {
        // get the operational param with the tier of the rent vehicle
        operationalService
            .findAll()
            .stream()
            .filter(op -> op.getTier().equals(thisVehicle.getTier()))
            .findFirst()
            .ifPresent(op -> thisOperational = op);
    }

    static void getRent(RentService rentService) {
        rentService
            .findAll()
            .stream()
            .filter(rent -> rent.getLicensePlate().equals(licensePlate.getValue()))
            .findFirst()
            .ifPresent(rent -> thisRent = rent);
    }

    static void getVehicle(VehicleService vehicleService) {
        // get the vehicle with the plate
        vehicleService
            .findAll()
            .stream()
            .filter(v -> v.getLicensePlate().equals(licensePlate.getValue()))
            .findFirst()
            .ifPresent(v -> thisVehicle = v);
    }


    static void getPlate(RentService rentService) {
        // get All plates for the cars that rent's return_date is today
        rentService
            .findAll()
            .stream()
            .filter(rent -> rent.getStatus().equals(RentStatus.EFFECTED))
            .filter(rent -> rent.getStatus().equals(RentStatus.ACTIVE))
            .filter(rent -> rent.getReturnDate().equals(LocalDate.now()))
            .forEach(rent -> plates.add(rent.getLicensePlate()));

    }

    
}

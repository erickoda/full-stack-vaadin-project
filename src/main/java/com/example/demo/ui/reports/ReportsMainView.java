package com.example.demo.ui.reports;

import org.vaadin.reports.PrintPreviewReport;

import com.example.demo.backend.client.ClientService;
import com.example.demo.backend.rent.RentService;
import com.example.demo.backend.vehicle.VehicleEntity;
import com.example.demo.backend.vehicle.VehicleService;
import com.example.demo.backend.vehicle.VehicleStatus;
import com.example.demo.backend.vehicle.VehicleTier;
import com.example.demo.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value="reports", layout = MainLayout.class)
@PageTitle("Generate Reports")
@RolesAllowed("GERENTE")
public class ReportsMainView extends VerticalLayout {
    H1 Title = new H1("Reports");
    
    /**
     * Constructor to the Reports Main View of web application
    */
    public ReportsMainView(
        ClientService clientService,
        RentService rentService,
        VehicleService vehicleService
        ) {
        add(
            Title,
            VehicleReports(vehicleService),
            ClientsReports(clientService)
        );
    }
    
    /**
     * Creates a component that shows percentage data of vehicles by status and tier
     * 
     * @param   vehicleService        A VehicleService object
     * @return  Component             A component with the percentage of clients with age between 0 and 10,
     * @see                           Component
    */
    public Component VehicleReports(VehicleService vehicleService) {

        HorizontalLayout vehicleStatus = new HorizontalLayout();
        HorizontalLayout vehicleTier = new HorizontalLayout();
        VerticalLayout reports = new VerticalLayout();

        for (VehicleStatus status : VehicleStatus.values()) {

            float percentage = (float) vehicleService.percentageByStatus(status) * 100;
            String percentageString = String.format("%.2f", percentage);

            vehicleStatus.add(
                new Span(status.toString() + ": " + percentageString + "%")
            );
        }

        for (VehicleTier tier : VehicleTier.values()) {

            float percentage = (float) vehicleService.percentageByTier(tier) * 100;
            String percentageString = String.format("%.2f", percentage);

            vehicleTier.add(
                new Span(tier.toString() + ": " + percentageString + "%")
            );
        }

        reports.add(new H2("Vehicle"));
        reports.add(new H4("Status"));
        reports.add(vehicleStatus);
        reports.add(new H4("Tier"));
        reports.add(vehicleTier);

        // reports.addClassNames(null);
        
        return reports;
    }


    /**
     * Creates a component that shows the percentage of clients with age between 0 and 10,
     * 10 and 20, and so on
     * 
     * @param   clientService         A ClientService object
     * @return  Component             A component with the percentage of clients with age between 0 and 10,
     * @see                           Component
    */
    public Component ClientsReports(ClientService clientService) {

        HorizontalLayout clientBirthday = new HorizontalLayout();
        VerticalLayout reports = new VerticalLayout();

        for (int i = 0; i < 10; i++) {

            float percentage = (float) clientService.percentageOfClientsWithAgeBetween(i*10, (i+1)*10) * 100;

            clientBirthday.add(
                new Span(i*10 + " e " + (i + 1)*10 + ": " + String.format("%.2f", percentage) + "%")
            );
        }

        reports.add(new H2("Clients"));
        reports.add(new H4("Age"));
        reports.add(clientBirthday);

        return reports;
    }
}

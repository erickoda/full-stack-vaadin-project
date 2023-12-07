package com.example.demo.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;


@Route(value = "", layout = MainLayout.class)
@PageTitle("Locadora")
@PermitAll
public class MainView extends VerticalLayout {
    
    public MainView() {
        addClassName("main-view");
        setSizeFull();

        add(
            new H1("Main"),
            new Paragraph("Main")
        );
    }
}

package com.example.demo.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

// @Theme(variant = Lumo.DARK)
@Route(value = "login")
@PageTitle("Login | Locadora")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver, AppShellConfigurator {
    
    private LoginForm login = new LoginForm();

    public LoginView() {

        addClassName("login-view");
        setSizeFull();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        add(
            new H1("Locadora"),
            login
        );
    }
    
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (
            beforeEnterEvent
                .getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")   
        ) {
            login.setError(true);
        }
    }
}

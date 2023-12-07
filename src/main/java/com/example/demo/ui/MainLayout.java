package com.example.demo.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import com.example.demo.security.SecurityService;
import com.example.demo.ui.cars.ManagerView;

public class MainLayout extends AppLayout {

    private final SecurityService securityService;
    
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        themeList.add(Lumo.DARK);

        H1 logo = new H1("Locadora");
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Log out", e -> securityService.logout());
        
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink mainView = new RouterLink("Main", MainView.class);
        mainView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink managerView = new RouterLink("Manager", ManagerView.class);
        managerView.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
            mainView,
            managerView
        ));
    }
}

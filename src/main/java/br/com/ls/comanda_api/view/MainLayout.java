package br.com.ls.comanda_api.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        // HEADER
        H1 logo = new H1("Comanda+");
        logo.getStyle().set("font-size", "1.5em").set("margin", "0");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.getStyle().set("padding", "0 1em");

        addToNavbar(header);

        // MENU LATERAL
        RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
        RouterLink mesasLink = new RouterLink("Mesas", MesasView.class);
        RouterLink estoqueLink = new RouterLink("Estoque", EstoqueView.class);
        RouterLink caixaLink = new RouterLink("Fluxo de Caixa", CaixaView.class);
        RouterLink usuariosLink = new RouterLink("Usuários", UsuarioListView.class);
        RouterLink configuracoesLink = new RouterLink("Configurações", ConfiguracoesView.class);

        addToDrawer(
                dashboardLink,
                mesasLink,
                estoqueLink,
                caixaLink,
                usuariosLink,
                configuracoesLink
        );
    }
}

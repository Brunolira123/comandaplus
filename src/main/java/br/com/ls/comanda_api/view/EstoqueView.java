package br.com.ls.comanda_api.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Estoque | Comanda+")
@Route(value = "estoque", layout = MainLayout.class)
public class EstoqueView extends VerticalLayout {
    public EstoqueView() {
        add(new H2("Gestão de Estoque"));
    }
}
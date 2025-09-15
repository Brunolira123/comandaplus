package br.com.ls.comanda_api.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Fluxo de Caixa | Comanda+")
@Route(value = "caixa", layout = MainLayout.class)
public class CaixaView extends VerticalLayout {
    public CaixaView() {
        add(new H2("Fluxo de Caixa"));
    }
}
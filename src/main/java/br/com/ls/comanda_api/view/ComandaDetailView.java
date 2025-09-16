package br.com.ls.comanda_api.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@PageTitle("Comanda | Comanda+")
@Route(value = "comanda", layout = MainLayout.class)
public class ComandaDetailView extends VerticalLayout implements HasUrlParameter<Integer> {

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer idComanda) {
        removeAll();
        add(new H2("Comanda #" + idComanda + " - Detalhes aqui futuramente"));
    }
}


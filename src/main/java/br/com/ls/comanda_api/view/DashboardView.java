package br.com.ls.comanda_api.view;

import br.com.ls.comanda_api.model.Mesa;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Dashboard | Comanda+")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        setPadding(true);
        setSpacing(true);

        add(new H2("Bem-vindo ao Dashboard do Comanda+ 🚀"));

        // Mock de mesas
        List<Mesa> mesas = new ArrayList<>();
        mesas.add(new Mesa(1, true));
        mesas.add(new Mesa(2, false));
        mesas.add(new Mesa(3, true));
        mesas.add(new Mesa(4, false));
        mesas.add(new Mesa(5, true));

        long abertas = mesas.stream().filter(Mesa::isAberta).count();
        long fechadas = mesas.stream().filter(m -> !m.isAberta()).count();

        // Mock de caixa e pedidos
        double caixaHoje = 350.50;
        int pedidosPendentes = 7;

        // Criar cards
        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setSpacing(true);
        cardLayout.setWidthFull();

        cardLayout.add(createCard("Mesas Abertas", String.valueOf(abertas)));
        cardLayout.add(createCard("Mesas Fechadas", String.valueOf(fechadas)));
        cardLayout.add(createCard("Caixa do Dia", "R$ " + caixaHoje));
        cardLayout.add(createCard("Pedidos Pendentes", String.valueOf(pedidosPendentes)));

        add(cardLayout);
    }

    private Div createCard(String title, String value) {
        Div card = new Div();
        card.getStyle().set("padding", "1em")
                .set("border", "1px solid #ccc")
                .set("border-radius", "8px")
                .set("width", "200px")
                .set("text-align", "center")
                .set("background-color", "#f8f9fa")
                .set("box-shadow", "2px 2px 5px rgba(0,0,0,0.1)");

        Span cardTitle = new Span(title);
        cardTitle.getStyle().set("font-weight", "bold")
                .set("display", "block")
                .set("margin-bottom", "0.5em");

        Span cardValue = new Span(value);
        cardValue.getStyle().set("font-size", "1.5em");

        card.add(cardTitle, cardValue);
        return card;
    }
}

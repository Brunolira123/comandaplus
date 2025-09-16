package br.com.ls.comanda_api.view;

import br.com.ls.comanda_api.model.Comanda;
import br.com.ls.comanda_api.model.Mesa;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Mesas | Comanda+")
@Route(value = "mesas", layout = MainLayout.class)
public class MesasView extends VerticalLayout {

    private List<Mesa> mesas;

    public MesasView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // Mock de mesas com algumas comandas abertas
        mesas = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            Mesa mesa = new Mesa(i);

            // Criando comandas mock com nomes de clientes
            if (i % 2 == 0) {
                Comanda comanda = new Comanda(i);
                comanda.setCliente("Cliente " + i);
                mesa.getComandas().add(comanda);
            }

            mesas.add(mesa);
        }

        // Layout flexível para os cards
        FlexLayout cardsLayout = new FlexLayout();
        cardsLayout.getStyle().set("flex-wrap", "wrap");
        cardsLayout.getStyle().set("gap", "16px");
        cardsLayout.setWidthFull();

        for (Mesa mesa : mesas) {
            VerticalLayout card = new VerticalLayout();
            card.setWidth("150px");
            card.setHeight("auto");
            card.setSpacing(false);
            card.setPadding(true);
            card.getStyle().set("border", "1px solid #ccc");
            card.getStyle().set("border-radius", "12px");
            card.getStyle().set("box-shadow", "2px 2px 6px rgba(0,0,0,0.1)");

            // Cor do card: verde se tiver comanda, cinza se não
            if (!mesa.getComandas().isEmpty()) {
                card.getStyle().set("background-color", "#d4edda"); // verde claro
            } else {
                card.getStyle().set("background-color", "#e2e3e5"); // cinza claro
            }

            // Conteúdo do card
            card.add(new H3("Mesa " + mesa.getNumero()));
            card.add("Comandas: " + mesa.getComandas().size());

            // Listar nomes dos clientes
            if (!mesa.getComandas().isEmpty()) {
                for (Comanda c : mesa.getComandas()) {
                    card.add("→ " + c.getCliente());
                }
            }

            // Botão para abrir a mesa
            Button abrir = new Button("Ver Mesa", e -> {
                UI.getCurrent().navigate(MesaDetailView.class, mesa.getNumero());
            });
            card.add(abrir);

            cardsLayout.add(card);
        }

        add(cardsLayout);
    }
}

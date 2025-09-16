package br.com.ls.comanda_api.view;


import br.com.ls.comanda_api.model.Comanda;
import br.com.ls.comanda_api.model.Mesa;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@PageTitle("Detalhes da Mesa | Comanda+")
@Route(value = "mesa", layout = MainLayout.class)
public class MesaDetailView extends VerticalLayout implements HasUrlParameter<Integer> {

    private Mesa mesa;

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer numeroMesa) {
        // mock: mesa com 2 comandas
        mesa = new Mesa(numeroMesa);
        mesa.getComandas().add(new Comanda(1));
        mesa.getComandas().add(new Comanda(2));

        removeAll();
        add(new H2("Mesa " + mesa.getNumero()));

        for (Comanda c : mesa.getComandas()) {
            HorizontalLayout linha = new HorizontalLayout();
            linha.setSpacing(true);

            linha.add(new H3("Comanda #" + c.getId()));

            Button abrir = new Button("Abrir", e -> {
                getUI().ifPresent(ui -> ui.navigate(ComandaDetailView.class, c.getId()));
            });

            Button fechar = new Button("Fechar", e -> {
                c.setAberta(false);
                Notification.show("Comanda #" + c.getId() + " fechada!");
                mesa.getComandas().remove(c); // remove da mesa (mock)
                setParameter(null, mesa.getNumero()); // recarrega tela
            });

            linha.add(abrir, fechar);
            add(linha);
        }

        add(new Button("Nova Comanda", e -> {
            Comanda nova = new Comanda(mesa.getComandas().size() + 1);
            mesa.getComandas().add(nova);
            setParameter(null, mesa.getNumero()); // recarrega tela
        }));
    }
}

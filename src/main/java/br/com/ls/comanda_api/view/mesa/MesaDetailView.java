package br.com.ls.comanda_api.view.mesa;

import br.com.ls.comanda_api.model.Comanda;
import br.com.ls.comanda_api.model.Mesa;
import br.com.ls.comanda_api.service.MesaService;
import br.com.ls.comanda_api.view.ComandaDetailView;
import br.com.ls.comanda_api.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@PageTitle("Detalhes da Mesa | Comanda+")
@Route(value = "mesa/detalhes", layout = MainLayout.class)
public class MesaDetailView extends VerticalLayout implements HasUrlParameter<Long> {

    private final MesaService mesaService;
    private Mesa mesa;

    public MesaDetailView(MesaService mesaService) {
        this.mesaService = mesaService;
        setSizeFull();
        setPadding(true);
        setSpacing(true);
    }

    @Override
    public void setParameter(BeforeEvent event, Long mesaId) {
        mesa = mesaService.buscarPorId(mesaId).orElse(null);
        removeAll();

        if (mesa == null) {
            Notification.show("Mesa não encontrada!");
            return;
        }

        add(new H2("Mesa " + mesa.getNumero()));

        for (Comanda c : mesa.getComandas()) {
            HorizontalLayout linha = new HorizontalLayout();
            linha.setSpacing(true);

            linha.add(new H3("Comanda #" + c.getId()));

            Button abrir = new Button("Abrir", e ->
                    getUI().ifPresent(ui -> ui.navigate(ComandaDetailView.class)));

            Button fechar = new Button("Fechar", e -> {
                c.setAberta(false);
                mesaService.atualizarMesa(mesa);
                Notification.show("Comanda #" + c.getId() + " fechada!");
                setParameter(event, mesa.getId()); // recarrega tela
            });

            linha.add(abrir, fechar);
            add(linha);
        }

        add(new Button("Nova Comanda", e -> {
            Comanda nova = new Comanda();
            nova.setMesa(mesa);
            nova.setCliente("Cliente " + (mesa.getComandas().size() + 1));
            mesa.getComandas().add(nova);
            mesaService.atualizarMesa(mesa);
            setParameter(event, mesa.getId()); // recarrega tela
        }));
    }
}

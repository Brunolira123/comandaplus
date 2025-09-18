package br.com.ls.comanda_api.view.comanda;

import br.com.ls.comanda_api.model.Comanda;
import br.com.ls.comanda_api.model.ItemPedido;
import br.com.ls.comanda_api.service.ComandaService;
import br.com.ls.comanda_api.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

@PageTitle("Detalhes da Comanda | Comanda+")
@Route(value = "comanda", layout = MainLayout.class)
public class ComandaDetailView extends VerticalLayout implements HasUrlParameter<Long> {

    private final ComandaService comandaService;
    private Comanda comanda;

    public ComandaDetailView(ComandaService comandaService) {
        this.comandaService = comandaService;
        setSizeFull();
        setPadding(true);
        setSpacing(true);
    }

    @Override
    public void setParameter(BeforeEvent event, Long id) {
        comanda = comandaService.buscarPorId(id).orElse(null);
        removeAll();

        if (comanda == null) {
            Notification.show("Comanda não encontrada!");
            return;
        }

        add(new H2("Comanda #" + comanda.getId() + " - Mesa " + comanda.getMesa().getNumero()));
        add(new H3("Cliente: " + comanda.getCliente()));
        add(new H3("Status: " + (comanda.isAberta() ? "Aberta" : "Fechada")));

        for (ItemPedido item : comanda.getItens()) {
            add(new H3(item.getDescricao() + " x" + item.getQuantidade()));
        }

        TextField novoItem = new TextField("Novo Item");
        Button adicionarItem = new Button("Adicionar", e -> {
            ItemPedido item = new ItemPedido();
            item.setDescricao(novoItem.getValue());
            item.setQuantidade(1);
            comanda.getItens().add(item);
            comandaService.atualizarComanda(comanda);
            setParameter(event, comanda.getId()); // recarrega tela
        });

        add(new HorizontalLayout(novoItem, adicionarItem));
        add(new Button("Fechar Comanda", e -> {
            comanda.setAberta(false);
            comandaService.atualizarComanda(comanda);
            Notification.show("Comanda fechada!");
            setParameter(event, comanda.getId()); // recarrega tela
        }));
    }
}

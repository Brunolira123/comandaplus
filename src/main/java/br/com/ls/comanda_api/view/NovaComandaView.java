package br.com.ls.comanda_api.view;

import br.com.ls.comanda_api.model.Comanda;
import br.com.ls.comanda_api.model.Mesa;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Nova Comanda | Comanda+")
@Route(value = "nova-comanda", layout = MainLayout.class)
public class NovaComandaView extends VerticalLayout {

    public NovaComandaView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        TextField clienteField = new TextField("Nome do Cliente");

        Button criar = new Button("Criar Comanda", e -> {
            String nomeCliente = clienteField.getValue();
            if (nomeCliente == null || nomeCliente.isEmpty()) {
                Notification.show("Informe o nome do cliente!");
                return;
            }

            // Aqui você pode associar a comanda a uma mesa (mock ou banco)
            Comanda comanda = new Comanda(999); // id mock
            comanda.setCliente(nomeCliente);

            Notification.show("Comanda criada para: " + nomeCliente);

            // Redireciona para ComandaView
            UI.getCurrent().navigate(ComandaView.class, comanda.getId());
        });

        add(clienteField, criar);
    }
}

package br.com.ls.comanda_api.view.comanda;

import br.com.ls.comanda_api.model.Comanda;
import br.com.ls.comanda_api.model.Mesa;
import br.com.ls.comanda_api.service.ComandaService;
import br.com.ls.comanda_api.service.MesaService;
import br.com.ls.comanda_api.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Nova Comanda | Comanda+")
@Route(value = "comanda/nova", layout = MainLayout.class)
public class ComandaCreateView extends VerticalLayout {

    private final ComandaService comandaService;
    private final MesaService mesaService;

    private ComboBox<Mesa> mesaCombo;
    private TextField clienteField;
    private Button salvar;

    public ComandaCreateView(ComandaService comandaService, MesaService mesaService) {
        this.comandaService = comandaService;
        this.mesaService = mesaService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 titulo = new H2("Nova Comanda");

        mesaCombo = new ComboBox<>("Mesa", mesaService.listarMesas());
        mesaCombo.setItemLabelGenerator(m -> "Mesa " + m.getNumero());

        clienteField = new TextField("Cliente");

        salvar = new Button("Salvar", e -> {
            if (mesaCombo.isEmpty() || clienteField.isEmpty()) {
                Notification.show("Mesa e Cliente são obrigatórios!");
                return;
            }

            Comanda comanda = new Comanda();
            comanda.setMesa(mesaCombo.getValue());
            comanda.setCliente(clienteField.getValue());
            comanda.setAberta(true);

            comandaService.criarComanda(comanda);
            Notification.show("Comanda criada com sucesso!");

            clienteField.clear();
            mesaCombo.clear();
        });

        add(titulo, new FormLayout(mesaCombo, clienteField, salvar));
    }
}

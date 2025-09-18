package br.com.ls.comanda_api.view.mesa;

import br.com.ls.comanda_api.service.MesaService;
import br.com.ls.comanda_api.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Nova Mesa | Comanda+")
@Route(value = "mesa/nova", layout = MainLayout.class)
public class MesaCreateView extends VerticalLayout {

    private final MesaService mesaService;
    private final IntegerField numeroField;
    private final Button salvar;

    public MesaCreateView(MesaService mesaService) {
        this.mesaService = mesaService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 titulo = new H2("Nova Mesa");

        numeroField = new IntegerField("Número da Mesa");

        salvar = new Button("Salvar", e -> {
            if (numeroField.isEmpty()) {
                Notification.show("Número da mesa é obrigatório!");
                return;
            }
            mesaService.criarMesa(numeroField.getValue());
            Notification.show("Mesa criada com sucesso!");
            numeroField.clear();
        });

        add(titulo, new FormLayout(numeroField, salvar));
    }
}

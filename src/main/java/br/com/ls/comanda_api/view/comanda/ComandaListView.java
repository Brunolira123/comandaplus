package br.com.ls.comanda_api.view.comanda;

import br.com.ls.comanda_api.model.Comanda;
import br.com.ls.comanda_api.service.ComandaService;
import br.com.ls.comanda_api.view.ComandaDetailView;
import br.com.ls.comanda_api.view.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

@PageTitle("Comandas | Comanda+")
@Route(value = "comandas", layout = MainLayout.class)
public class ComandaListView extends VerticalLayout {

    private final ComandaService comandaService;
    private final Grid<Comanda> grid;

    public ComandaListView(ComandaService comandaService) {
        this.comandaService = comandaService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 titulo = new H2("Comandas");

        Button novaComanda = new Button("Nova Comanda", e -> UI.getCurrent().navigate(ComandaCreateView.class));
        HorizontalLayout actions = new HorizontalLayout(novaComanda);

        grid = new Grid<>(Comanda.class, false);
        grid.addColumn(Comanda::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(c -> c.getMesa().getNumero()).setHeader("Mesa").setAutoWidth(true);
        grid.addColumn(Comanda::getCliente).setHeader("Cliente").setAutoWidth(true);
        grid.addColumn(Comanda::isAberta).setHeader("Aberta").setAutoWidth(true);

        grid.addComponentColumn(comanda -> {
            Button detalhes = new Button("Detalhes", e ->
                    UI.getCurrent().navigate(ComandaDetailView.class,
                            new RouteParameters("id", String.valueOf(comanda.getId()))));
            Button excluir = new Button("Excluir", e -> {
                comandaService.deletarComanda(comanda.getId());
                Notification.show("Comanda excluída!");
                atualizarGrid();
            });
            return new HorizontalLayout(detalhes, excluir);
        }).setHeader("Ações");

        atualizarGrid();
        add(titulo, actions, grid);
    }

    private void atualizarGrid() {
        grid.setItems(comandaService.listarComandas());
    }
}

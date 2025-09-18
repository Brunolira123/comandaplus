package br.com.ls.comanda_api.view.mesa;

import br.com.ls.comanda_api.model.Mesa;
import br.com.ls.comanda_api.service.MesaService;
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

@PageTitle("Mesas | Comanda+")
@Route(value = "mesas", layout = MainLayout.class)
public class MesaListView extends VerticalLayout {

    private final MesaService mesaService;
    private final Grid<Mesa> grid;

    public MesaListView(MesaService mesaService) {
        this.mesaService = mesaService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 titulo = new H2("Mesas");

        Button novaMesa = new Button("Nova Mesa", e -> UI.getCurrent().navigate(MesaCreateView.class));
        HorizontalLayout actions = new HorizontalLayout(novaMesa);

        grid = new Grid<>(Mesa.class, false);
        grid.addColumn(Mesa::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Mesa::getNumero).setHeader("Número").setAutoWidth(true);
        grid.addColumn(Mesa::getAtiva).setHeader("Ativa").setAutoWidth(true);

        grid.addComponentColumn(mesa -> {
            Button detalhes = new Button("Detalhes", e ->
                    UI.getCurrent().navigate(MesaDetailView.class,
                            new RouteParameters("id", String.valueOf(mesa.getId()))));
            Button excluir = new Button("Excluir", e -> {
                mesaService.deletarMesa(mesa.getId());
                Notification.show("Mesa excluída!");
                atualizarGrid();
            });
            return new HorizontalLayout(detalhes, excluir);
        }).setHeader("Ações");

        atualizarGrid();
        add(titulo, actions, grid);
    }

    private void atualizarGrid() {
        grid.setItems(mesaService.listarMesas());
    }
}

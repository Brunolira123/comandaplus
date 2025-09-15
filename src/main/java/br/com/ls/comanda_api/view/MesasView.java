package br.com.ls.comanda_api.view;

import br.com.ls.comanda_api.model.Mesa;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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
        setPadding(true);
        setSpacing(true);

        // Mock de mesas
        mesas = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mesas.add(new Mesa(i, i % 2 == 0)); // algumas abertas, outras fechadas
        }

        // Grid de mesas
        Grid<Mesa> grid = new Grid<>(Mesa.class);
        grid.setItems(mesas);
        grid.removeColumnByKey("aberta"); // vamos customizar status
        grid.addColumn(Mesa::getNumero).setHeader("Número");
        grid.addColumn(m -> m.isAberta() ? "Aberta" : "Fechada").setHeader("Status");

        // Coluna de ação: abrir/fechar comanda
        grid.addComponentColumn(m -> {
            Button btn = new Button(m.isAberta() ? "Fechar Comanda" : "Abrir Comanda");
            btn.addClickListener(e -> {
                m.setAberta(!m.isAberta()); // alterna status
                grid.getDataProvider().refreshItem(m);
            });
            return btn;
        }).setHeader("Ação");

        add(grid);
    }
}
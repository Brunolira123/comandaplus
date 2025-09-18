package br.com.ls.comanda_api.view;

import br.com.ls.comanda_api.model.Comanda;
import br.com.ls.comanda_api.model.ItemPedido;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.ArrayList;

@PageTitle("Comanda | Comanda+")
@Route(value = "comanda", layout = MainLayout.class)
public class ComandaView extends VerticalLayout implements HasUrlParameter<Integer> {

    private Comanda comanda;
    private Grid<ItemPedido> grid;
    private TextField clienteField;

    public ComandaView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // Campo para nome do cliente
        clienteField = new TextField("Nome do Cliente");
        clienteField.setWidth("300px");

        // Grid de itens
        grid = new Grid<>(ItemPedido.class, false);
        grid.addColumn(ItemPedido::getDescricao).setHeader("Item");
        grid.addColumn(ItemPedido::getQuantidade).setHeader("Qtd");
        grid.addColumn(ItemPedido::getPreco).setHeader("Preço Unit.");
        grid.addColumn(ItemPedido::getTotal).setHeader("Subtotal");

        // Botões
        Button addItem = new Button("Adicionar Item", e -> {
            comanda.getItens().add(new ItemPedido("Cerveja", 1, 10.0));
            atualizarGrid();
        });

        Button removerItem = new Button("Remover Selecionado", e -> {
            ItemPedido selecionado = grid.asSingleSelect().getValue();
            if (selecionado != null) {
                comanda.getItens().remove(selecionado);
                atualizarGrid();
            }
        });

        Button fecharComanda = new Button("Fechar Comanda", e -> {
            comanda.setCliente(clienteField.getValue()); // garante que o nome seja salvo
            UI.getCurrent().navigate("fechamento/" + comanda.getId());
        });

        HorizontalLayout botoes = new HorizontalLayout(addItem, removerItem, fecharComanda);

        add(new H2("Comanda"), clienteField, grid, botoes);
    }

    @Override
    public void setParameter(BeforeEvent event, Integer numero) {
        // Mock da comanda, futuramente busca do banco
        comanda = new Comanda();
        comanda.setItens(new ArrayList<>());
        comanda.setCliente(""); // inicia vazio
        clienteField.setValue(comanda.getCliente());

        atualizarGrid();
    }

    private void atualizarGrid() {
        grid.setItems(comanda.getItens());
    }
}

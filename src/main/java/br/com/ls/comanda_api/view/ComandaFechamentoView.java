package br.com.ls.comanda_api.view;

import br.com.ls.comanda_api.model.Comanda;
import br.com.ls.comanda_api.model.ItemPedido;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.NumberFormat;
import java.util.Locale;

@PageTitle("Fechamento | Comanda+")
@Route(value = "fechamento", layout = MainLayout.class)
public class ComandaFechamentoView extends VerticalLayout implements HasUrlParameter<Integer> {

    private Comanda comanda;
    private Grid<ItemPedido> grid;
    private Span totalLabel;
    private NumberField valorRecebido;
    private ComboBox<String> formaPagamento;

    public ComandaFechamentoView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Resumo da Comanda"));

        // Grid de itens
        grid = new Grid<>(ItemPedido.class, false);
        grid.addColumn(ItemPedido::getDescricao).setHeader("Item");
        grid.addColumn(ItemPedido::getQuantidade).setHeader("Qtd");
        grid.addColumn(ItemPedido::getPreco).setHeader("Preço Unit.");
        grid.addColumn(ItemPedido::getTotal).setHeader("Subtotal");

        totalLabel = new Span("Total: R$ 0,00");

        // Forma de pagamento
        formaPagamento = new ComboBox<>("Forma de Pagamento");
        formaPagamento.setItems("Dinheiro", "Cartão", "Pix");
        formaPagamento.setValue("Dinheiro");

        // Valor recebido
        valorRecebido = new NumberField("Valor Recebido");
        valorRecebido.setStep(0.01);
        valorRecebido.setValueChangeMode(ValueChangeMode.EAGER);

        // Botões
        Button confirmarPagamento = new Button("Confirmar Pagamento", e -> processarPagamento());
        Button voltar = new Button("Voltar", e -> UI.getCurrent().navigate("comanda/" + comanda.getId()));

        HorizontalLayout botoes = new HorizontalLayout(confirmarPagamento, voltar);

        add(grid, totalLabel, formaPagamento, valorRecebido, botoes);
    }

    @Override
    public void setParameter(BeforeEvent event, Integer numero) {
        // Mock da comanda
        comanda = new Comanda();
        comanda.getItens().add(new ItemPedido("Cerveja", 2, 10.0));
        comanda.getItens().add(new ItemPedido("Espetinho de Frango", 3, 8.0));
        comanda.getItens().add(new ItemPedido("Refrigerante", 1, 6.0));

        grid.setItems(comanda.getItens());

        double total = comanda.getItens().stream().mapToDouble(ItemPedido::getTotal).sum();
        totalLabel.setText("Total: " + formatMoeda(total));

        // Preenche valor recebido com total por padrão
        valorRecebido.setValue(total);
    }

    private void processarPagamento() {
        double total = comanda.getItens().stream().mapToDouble(ItemPedido::getTotal).sum();
        String forma = formaPagamento.getValue();

        Dialog dialog = new Dialog();
        dialog.setWidth("300px");

        if ("Dinheiro".equals(forma)) {
            double recebido = valorRecebido.getValue() != null ? valorRecebido.getValue() : 0.0;
            double troco = recebido - total;
            if (troco < 0) {
                Notification.show("Valor recebido insuficiente!");
                return;
            }
            dialog.add(new Span("Pagamento em Dinheiro confirmado!"));
            dialog.add(new Span("Troco: " + formatMoeda(troco > 0 ? troco : 0.0)));

        } else if ("Pix".equals(forma)) {
            dialog.add(new Span("Pagamento via PIX"));
            // Mock de QR Code (pode substituir por imagem real)
            Image qr = new Image("https://via.placeholder.com/150.png?text=PIX+QR", "QR Code PIX");
            dialog.add(qr);
        } else if ("Cartão".equals(forma)) {
            dialog.add(new Span("Pagamento via Cartão confirmado!"));
        }


        dialog.open();
    }

    private String formatMoeda(double valor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }
}

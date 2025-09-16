package br.com.ls.comanda_api.view;

import br.com.ls.comanda_api.model.Produto;
import br.com.ls.comanda_api.repository.ProdutoRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Produtos | Comanda+")
@Route(value = "produtos", layout = MainLayout.class)
public class ProdutoView extends VerticalLayout {

    private ProdutoRepository produtoRepository;

    private Grid<Produto> grid;
    private TextField nomeField;
    private NumberField precoField;
    private NumberField quantidadeField;

    @Autowired
    public ProdutoView(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        nomeField = new TextField("Nome");
        precoField = new NumberField("Preço");
        precoField.setStep(0.01);
        quantidadeField = new NumberField("Quantidade");
        quantidadeField.setStep(1);

        Button adicionar = new Button("Adicionar", e -> adicionarProduto());
        Button atualizar = new Button("Atualizar", e -> atualizarProduto());
        Button excluir = new Button("Excluir", e -> excluirProduto());

        HorizontalLayout form = new HorizontalLayout(nomeField, precoField, quantidadeField, adicionar, atualizar, excluir);

        grid = new Grid<>(Produto.class);
        grid.setColumns("id", "nome", "preco", "quantidade");
        grid.asSingleSelect().addValueChangeListener(e -> preencherForm(e.getValue()));

        add(form, grid);

        atualizarGrid();
    }

    private void adicionarProduto() {
        Produto p = Produto.builder()
                .descricao(nomeField.getValue())
                .preco(precoField.getValue())
                .quantidadeEstoque(quantidadeField.getValue().intValue())
                .build();
        produtoRepository.save(p);
        Notification.show("Produto adicionado!");
        atualizarGrid();
        limparForm();
    }

    private void atualizarProduto() {
        Produto selecionado = grid.asSingleSelect().getValue();
        if (selecionado != null) {
            selecionado.setDescricao(nomeField.getValue());
            selecionado.setPreco(precoField.getValue());
            selecionado.setQuantidadeEstoque(quantidadeField.getValue().intValue());
            produtoRepository.save(selecionado);
            Notification.show("Produto atualizado!");
            atualizarGrid();
            limparForm();
        }
    }

    private void excluirProduto() {
        Produto selecionado = grid.asSingleSelect().getValue();
        if (selecionado != null) {
            produtoRepository.delete(selecionado);
            Notification.show("Produto excluído!");
            atualizarGrid();
            limparForm();
        }
    }

    private void preencherForm(Produto p) {
        if (p != null) {
            nomeField.setValue(p.getDescricao());
            precoField.setValue(p.getPreco());
            quantidadeField.setValue((double) p.getQuantidadeEstoque());
        } else {
            limparForm();
        }
    }

    private void limparForm() {
        nomeField.clear();
        precoField.clear();
        quantidadeField.clear();
    }

    private void atualizarGrid() {
        grid.setItems(produtoRepository.findAll());
    }
}

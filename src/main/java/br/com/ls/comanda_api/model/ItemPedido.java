package br.com.ls.comanda_api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPedido {
    private String descricao;
    private int quantidade;
    private double preco;

    public ItemPedido() {
    }

    public ItemPedido(String descricao, int quantidade, double preco) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public double getTotal() {
        return quantidade * preco;
    }
}

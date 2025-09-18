package br.com.ls.comanda_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private int quantidade;
    private double preco;

    @ManyToOne
    @JoinColumn(name = "comanda_id")
    private Comanda comanda; // relaciona o item à comanda

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

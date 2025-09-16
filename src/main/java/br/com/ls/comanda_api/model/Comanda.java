package br.com.ls.comanda_api.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Comanda {
    private int id;
    private String cliente; // novo campo
    private boolean aberta;
    private List<ItemPedido> itens;

    public Comanda(int id) {
        this.id = id;
        this.cliente = "Cliente " + id; // padrão, pode ser alterado depois
        this.aberta = true;
        this.itens = new ArrayList<>();
    }
    public void setFechada(boolean b) {
    }
}
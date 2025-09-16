package br.com.ls.comanda_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Mesa {
    private int numero;
    private List<Comanda> comandas;

    public Mesa(int numero) {
        this.numero = numero;
        this.comandas = new ArrayList<>();
    }
}
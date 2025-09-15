package br.com.ls.comanda_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mesa {
    private int numero;
    private boolean aberta;
}

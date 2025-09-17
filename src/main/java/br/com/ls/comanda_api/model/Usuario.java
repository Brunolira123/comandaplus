package br.com.ls.comanda_api.model;

import br.com.ls.comanda_api.enuns.EPerfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // depois a gente coloca criptografia

    @Enumerated(EnumType.STRING)
    private EPerfil role; // ADMIN, CAIXA, GARCOM
}

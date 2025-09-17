package br.com.ls.comanda_api.config;

import br.com.ls.comanda_api.enuns.EPerfil;
import br.com.ls.comanda_api.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioService usuarioService;

    public DataInitializer(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioService.listarTodos().isEmpty()) {
            usuarioService.criarUsuario("admin", "admin123", EPerfil.ADMIN);
            System.out.println("Usuário admin criado: admin / admin123");
        }
    }
}

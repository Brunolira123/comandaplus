package br.com.ls.comanda_api.view.usuario;

import br.com.ls.comanda_api.enuns.EPerfil;
import br.com.ls.comanda_api.service.UsuarioService;
import br.com.ls.comanda_api.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Novo Usuário | Comanda+")
@Route(value = "usuario/novo", layout = MainLayout.class)
public class UsuarioCreateView extends VerticalLayout {

    private final UsuarioService usuarioService;
    private TextField nome;
    private PasswordField senha;
    private ComboBox<EPerfil> perfil;
    private Button salvar;

    public UsuarioCreateView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 titulo = new H2("Novo Usuário");

        nome = new TextField("Nome de usuário");
        senha = new PasswordField("Senha");
        perfil = new ComboBox<>("Perfil", EPerfil.values());

        salvar = new Button("Salvar", e -> {
            usuarioService.criarUsuario(
                    nome.getValue(),
                    senha.getValue(),
                    perfil.getValue()
            );
            Notification.show("Usuário criado com sucesso!");
            nome.clear();
            senha.clear();
            perfil.clear();
        });
        add(titulo, new FormLayout(nome, senha, perfil, salvar));
    }
}

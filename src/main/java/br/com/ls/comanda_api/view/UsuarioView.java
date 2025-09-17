package br.com.ls.comanda_api.view;

import br.com.ls.comanda_api.enuns.EPerfil;
import br.com.ls.comanda_api.model.Usuario;
import br.com.ls.comanda_api.service.UsuarioService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Usuário | Comanda+")
@Route(value = "usuario", layout = MainLayout.class)
public class UsuarioView extends VerticalLayout implements HasUrlParameter<Long> {

    private final UsuarioService usuarioService;

    private TextField nome;
    private PasswordField senha;
    private ComboBox<EPerfil> perfil;
    private Button salvar;

    private Usuario usuario; // se for edição

    public UsuarioView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 titulo = new H2("Usuário");

        nome = new TextField("Nome de usuário");
        senha = new PasswordField("Senha");
        perfil = new ComboBox<>("Perfil", EPerfil.values());

        salvar = new Button("Salvar", e -> salvarOuAtualizar());

        FormLayout form = new FormLayout(nome, senha, perfil, salvar);
        add(titulo, form);
    }

    private void salvarOuAtualizar() {
        if (usuario == null) {
            // novo usuário
            usuarioService.criarUsuario(
                    nome.getValue(),
                    senha.getValue(),
                    perfil.getValue()
            );
            Notification.show("Usuário criado com sucesso!");
        } else {
            // edição
            usuario.setUsername(nome.getValue());
            if (!senha.isEmpty()) {
                usuario.setPassword(usuarioService.encodePassword(senha.getValue()));
            }
            usuario.setRole(perfil.getValue());

            usuarioService.atualizarUsuario(usuario);
            Notification.show("Usuário atualizado com sucesso!");
        }

        nome.clear();
        senha.clear();
        perfil.clear();
    }

    @Override
    public void setParameter(BeforeEvent event, Long id) {
        if (id != null && id > 0) {
            usuario = usuarioService.buscarPorId(id).orElse(null);
            if (usuario != null) {
                nome.setValue(usuario.getUsername());
                perfil.setValue(usuario.getRole());
            }
        } else {
            usuario = null; // criando novo
            nome.clear();
            senha.clear();
            perfil.clear();
        }
    }
}

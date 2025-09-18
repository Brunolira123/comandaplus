package br.com.ls.comanda_api.view.usuario;

import br.com.ls.comanda_api.model.Usuario;
import br.com.ls.comanda_api.service.UsuarioService;
import br.com.ls.comanda_api.enuns.EPerfil;
import br.com.ls.comanda_api.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

@PageTitle("Editar Usuário | Comanda+")
@Route(value = "usuario/editar/:id", layout = MainLayout.class)
public class UsuarioEditView extends VerticalLayout implements BeforeEnterObserver {

    private final UsuarioService usuarioService;

    private TextField nome;
    private PasswordField senha;
    private ComboBox<EPerfil> perfil;
    private Button salvar;
    private Button cancelar;

    private Usuario usuario; // usuário que será editado

    public UsuarioEditView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 titulo = new H2("Editar Usuário");

        nome = new TextField("Nome de usuário");
        senha = new PasswordField("Senha (deixe em branco para não alterar)");
        perfil = new ComboBox<>("Perfil", EPerfil.values());

        salvar = new Button("Salvar", e -> salvarUsuario());
        cancelar = new Button("Cancelar", e -> getUI().ifPresent(ui -> ui.navigate(UsuarioListView.class)));

        HorizontalLayout buttons = new HorizontalLayout(salvar, cancelar);
        FormLayout form = new FormLayout(nome, senha, perfil, buttons);
        add(titulo, form);
    }

    private void salvarUsuario() {
        if (usuario != null) {
            if (nome.isEmpty() || perfil.isEmpty()) {
                Notification.show("Nome e Perfil são obrigatórios!");
                return;
            }

            usuario.setUsername(nome.getValue());
            if (!senha.isEmpty()) {
                usuario.setPassword(usuarioService.encodePassword(senha.getValue()));
            }
            usuario.setRole(perfil.getValue());
            usuarioService.atualizarUsuario(usuario);
            Notification.show("Usuário atualizado com sucesso!");

            // volta para a lista de usuários
            getUI().ifPresent(ui -> ui.navigate(UsuarioListView.class));
        }
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().get("id").ifPresent(idStr -> {
            Long id = Long.valueOf(idStr);
            usuario = usuarioService.buscarPorId(id).orElse(null);
            if (usuario != null) {
                nome.setValue(usuario.getUsername());
                perfil.setValue(usuario.getRole());
            } else {
                Notification.show("Usuário não encontrado!");
                event.forwardTo(UsuarioListView.class);
            }
        });
    }
}

package br.com.ls.comanda_api.view.usuario;

import br.com.ls.comanda_api.model.Usuario;
import br.com.ls.comanda_api.service.UsuarioService;
import br.com.ls.comanda_api.view.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

@PageTitle("Usuários | Comanda+")
@Route(value = "usuarios", layout = MainLayout.class)
public class UsuarioListView extends VerticalLayout {

    private final UsuarioService usuarioService;
    private final Grid<Usuario> grid;

    public UsuarioListView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 titulo = new H2("Usuários");

        // Botão novo usuário
        Button novo = new Button("Novo Usuário", e -> {
            UI.getCurrent().navigate(UsuarioCreateView.class);
        });

        HorizontalLayout actions = new HorizontalLayout(novo);

        // Grid
        grid = new Grid<>(Usuario.class, false);
        grid.addColumn(Usuario::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Usuario::getUsername).setHeader("Usuário").setAutoWidth(true);
        grid.addColumn(u -> u.getRole().name()).setHeader("Perfil").setAutoWidth(true);

        // Coluna de ações
        grid.addComponentColumn(usuario -> {
            Button editar = new Button("Editar", e -> {
                // Navega para edição passando o ID do usuário
                UI.getCurrent().navigate(UsuarioEditView.class,
                        new RouteParameters("id", String.valueOf(usuario.getId())));
            });
            Button excluir = new Button("Excluir", e -> {
                usuarioService.deletarUsuario(usuario.getId());
                Notification.show("Usuário excluído!");
                atualizarGrid();
            });
            return new HorizontalLayout(editar, excluir);
        }).setHeader("Ações");

        atualizarGrid();

        add(titulo, actions, grid);
    }

    private void atualizarGrid() {
        grid.setItems(usuarioService.listarTodos());
    }
}


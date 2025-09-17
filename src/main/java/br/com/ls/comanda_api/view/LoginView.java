package br.com.ls.comanda_api.view;

import br.com.ls.comanda_api.enuns.EPerfil;
import br.com.ls.comanda_api.model.Usuario;
import br.com.ls.comanda_api.repository.UsuarioRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route("")
public class LoginView extends VerticalLayout {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Título
        H1 title = new H1("Comanda +");

        // Formulário de login
        LoginForm loginForm = new LoginForm();
        LoginI18n loginI18n = LoginI18n.createDefault();
        loginI18n.setHeader(new LoginI18n.Header());
        loginI18n.getHeader().setTitle("Comanda +");
        loginI18n.getHeader().setDescription("Entre com suas credenciais");
        loginI18n.getForm().setTitle("Login");
        loginI18n.getForm().setPassword("Senha");
        loginI18n.getForm().setSubmit("Entrar");
        loginI18n.getForm().setForgotPassword("Esqueci a senha");
        loginForm.setI18n(loginI18n);

        // Listener único para login
        loginForm.addLoginListener(e -> {
            usuarioRepository.findByUsername(e.getUsername()).ifPresentOrElse(user -> {
                if (passwordEncoder.matches(e.getPassword(), user.getPassword())) {
                    Notification.show("Bem-vindo " + user.getUsername());
                    UI.getCurrent().navigate(DashboardView.class);
                } else {
                    loginForm.setError(true);
                }
            }, () -> loginForm.setError(true));
        });

        // Botão de debug para criar admin
        Button criarAdmin = new Button("Criar Usuário (debug)", ev -> {
            usuarioRepository.save(Usuario.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123"))
                    .role(EPerfil.ADMIN)
                    .build());
            Notification.show("Usuário admin criado: admin/123");
        });

        add(title, loginForm, criarAdmin);
    }
}

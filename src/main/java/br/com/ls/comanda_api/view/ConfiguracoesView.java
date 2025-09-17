package br.com.ls.comanda_api.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Configurações | Comanda+")
@Route(value = "configuracoes", layout = MainLayout.class)
public class ConfiguracoesView extends VerticalLayout {

    public ConfiguracoesView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 titulo = new H2("Configurações Gerais");

        // Exemplos de parâmetros de configuração
        NumberField taxaServico = new NumberField("Taxa de Serviço (%)");
        taxaServico.setValue(10.0);
        taxaServico.setStep(0.1);

        NumberField descontoMaximo = new NumberField("Desconto Máximo (%)");
        descontoMaximo.setValue(15.0);
        descontoMaximo.setStep(0.1);

        TextField nomeEstabelecimento = new TextField("Nome do Estabelecimento");
        nomeEstabelecimento.setValue("Comanda+");

        Button salvar = new Button("Salvar", e -> {
            // Aqui futuramente você pode salvar no banco
            Notification.show("Configurações salvas com sucesso!");
        });

        FormLayout form = new FormLayout();
        form.add(nomeEstabelecimento, taxaServico, descontoMaximo, salvar);
        form.setColspan(salvar, 2);

        add(titulo, form);
    }
}

package de.nelius.i18n;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.function.Function;

/**
 * Example application with a few components.
 *
 * @author Christian Nelius
 */
@SpringBootApplication
public class SimpleSpringApplication {

    @Bean
    public I18nTranslator i18nTranslator() {
        return new DefaultI18nTranslator();
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleSpringApplication.class, args);
    }

    @Theme("valo")
    @SpringUI(path = "components")
    @PreserveOnRefresh
    public static class VaadinUI extends UI {

        @Override
        protected void init(VaadinRequest request) {
            setContent(buildComponents());
        }

        private VerticalLayout buildComponents() {
            /**
             * Init components
             */
            Button button = new Button("#");
            TextField textField = new TextField();
            Label label = new Label("#");
            ComboBox<Item> comboBox = new ComboBox<>();
            comboBox.setItems(new Item(I18n.COMBOBOX_TEXT.setProvider(v -> v.is("number") ? String.valueOf(VaadinComponentUtil.indexOf(comboBox, v.getTarget())) : v.getVariable())::apply)), new Item(I18n.COMBOBOX_TEXT))
            ;
            /**
             * Change Locale programmatically
             */
            Button changeLocale = new Button("Change Locale");
            changeLocale.addClickListener(new Button.ClickListener() {

                private Locale locale = Locale.US;

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    if (locale.equals(Locale.US)) {
                        locale = Locale.GERMANY;
                    } else {
                        locale = Locale.US;
                    }
                    I18nBinder.changeLocale(locale);
                }
            });
            /**
             * Bind components
             */
            I18nBinder.bind(I18n.LABEL_TEXT, label::setValue);
            I18nBinder.bind(I18n.TEXT_FIELD_TEXT, textField::setValue);
            I18nBinder.bind(I18n.BUTTON_TEXT, button::setCaption);
            comboBox.setItemCaptionGenerator(I18nBinder.asFunction(comboBox.getDataProvider()::refreshAll, (Function<Item, String>) i -> i.getKey(),
                    v -> v.is("number") ? String.valueOf(VaadinComponentUtil.indexOf(comboBox, v.getTarget())) : v.getVariable())::apply);
            /**
             * Layout
             */
            VerticalLayout root = new VerticalLayout();
            HorizontalLayout components = new HorizontalLayout();
            components.addComponents(button, textField, label, comboBox);
            root.addComponents(changeLocale, components);
            return root;
        }

        /**
         * This is just a object wrapper for {@link I18n} to let {@link VaadinComponentUtil#indexOf(ComboBox, Object)} work properly.
         */
        private static class Item {

            private I18n key;

            public Item(I18n key) {
                this.key = key;
            }

            public I18n getKey() {
                return key;
            }
        }

    }

}

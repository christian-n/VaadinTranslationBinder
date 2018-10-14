package de.nelius.translation;

import de.nelius.translation.beans.BeanProperty;
import de.nelius.translation.beans.Beans;
import de.nelius.translation.i18n.key.I18nKey;
import de.nelius.translation.i18n.variable.VariableProvider;
import de.nelius.translation.i18n.variable.VariableQuery;

import java.util.function.Function;

/**
 * Helper enum for translation keys.
 *
 * @author Christian Nelius
 */
public enum I18n implements I18nKey<I18n> {

    BUTTON_TEXT, LABEL_TEXT, TEXT_FIELD_TEXT, COMBOBOX_TEXT;

    private Function<VariableQuery<I18n>, String> provider;
    private BeanProperty<VariableProvider> variableProvider = Beans.get(VariableProvider.class);

    @Override
    public String getKey() {
        return getClass().getSimpleName().toLowerCase() + "." + name().replace("_", ".").toLowerCase();
    }

    @Override
    public String getBundle() {
        return "i18n";
    }

    public I18nKey<I18n> instance(Function<VariableQuery<I18n>, String> provider) {
        return new I18nKey<I18n>() {
            @Override
            public String getKey() {
                return I18n.this.getKey();
            }

            @Override
            public String getBundle() {
                return I18n.this.getBundle();
            }

            @Override
            public Function<VariableQuery<I18n>, String> getVariableProvider() {
                return provider;
            }

            @Override
            public String getVariablePattern() {
                return I18n.this.getVariablePattern();
            }
        };
    }

    public I18n setProvider(Function<VariableQuery<I18n>, String> provider) {
        this.provider = provider;
        return this;
    }

    @Override
    public Function<VariableQuery<I18n>, String> getVariableProvider() {
        if (variableProvider.isPresent()) {
            return variableProvider.get()::variable;
        }
        return provider;
    }


    @Override
    public String getVariablePattern() {
        return "\\{([a-zA-Z0-9\\-_.]+)}";
    }
}

package de.nelius.i18n;

import java.util.function.Function;

/**
 * Helper enum for translation keys.
 *
 * @author Christian Nelius
 */
public enum I18n implements I18nKey<I18n> {

    BUTTON_TEXT, LABEL_TEXT, TEXT_FIELD_TEXT, COMBOBOX_TEXT;

    private Function<VariableQuery<I18n>, String> provider;

    @Override
    public String getKey() {
        return getClass().getSimpleName().toLowerCase() + "." + name().replace("_", ".").toLowerCase();
    }

    @Override
    public String getBundle() {
        return "i18n";
    }

    public I18n setProvider(Function<VariableQuery<I18n>, String> provider) {
        this.provider = provider;
        return this;
    }

    @Override
    public Function<VariableQuery<I18n>, String> getVariableProvider() {
        return Beans.get(VariableProvider.class).get()::variable;
    }


    @Override
    public String getVariablePattern() {
        return "\\{([a-zA-Z0-9\\-_.]+)}";
    }
}

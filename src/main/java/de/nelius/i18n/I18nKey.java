package de.nelius.i18n;

import java.util.function.Function;

/**
 * Key for {@link I18nTranslator}.
 *
 * @author Christian Nelius
 */
public interface I18nKey<T> {

    String getKey();

    String getBundle();

    Function<VariableQuery<T>, String> getVariableProvider();

    String getVariablePattern();

}

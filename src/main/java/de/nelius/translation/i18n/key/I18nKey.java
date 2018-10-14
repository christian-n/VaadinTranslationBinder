package de.nelius.translation.i18n.key;

import de.nelius.translation.i18n.translator.I18nTranslator;
import de.nelius.translation.i18n.variable.VariableQuery;

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

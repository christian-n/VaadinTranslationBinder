package de.nelius.translation.i18n.translator;

import de.nelius.translation.i18n.key.I18nKey;

import java.util.Locale;

public interface I18nTranslator {

    <T> String translate(I18nKey<T> key, Locale locale);

}

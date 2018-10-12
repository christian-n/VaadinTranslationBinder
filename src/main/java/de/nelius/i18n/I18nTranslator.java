package de.nelius.i18n;

import java.util.Locale;

public interface I18nTranslator {

    <T> String translate(I18nKey<T> key, Locale locale);

}

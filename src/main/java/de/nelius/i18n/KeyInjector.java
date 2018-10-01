package de.nelius.i18n;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Injector for keys in {@link java.util.ResourceBundle} properties. Can handle relative paths to other bundles.
 * <p>
 * Pattern for local bundle keys <b>{key#your.property}</b>.
 * Pattern for relative bundle keys <b>{key#path/to/bundle{your.property}}</b>
 * <p>
 * Uses {@link VariableInjector} for variable injection.
 *
 * @author Christian Nelius
 */
public class KeyInjector {

    private static final Pattern keyPattern = Pattern.compile("\\{key#([a-zA-Z0-9/\\-.]+)(\\{([a-zA-Z0-9\\-.]+)})?}");

    private VariableInjector variableInjector;
    private I18nTranslator i18nTranslator;
    private Locale locale;

    public KeyInjector(I18nTranslator i18nTranslator, Locale locale) {
        this.i18nTranslator = i18nTranslator;
        this.locale = locale;
    }

    public KeyInjector(VariableInjector variableInjector, I18nTranslator i18nTranslator, Locale locale) {
        this.variableInjector = variableInjector;
        this.i18nTranslator = i18nTranslator;
        this.locale = locale;
    }

    public String inject(String text) {
        Matcher matcher = keyPattern.matcher(text);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, i18nTranslator.translate(key(matcher), locale));
        }
        matcher.appendTail(buffer);
        if (variableInjector != null) {
            return variableInjector.inject(buffer.toString());
        }
        return buffer.toString();
    }

    private I18nKey key(Matcher matcher) {
        //NOT GROUP
        if (matcher.group(3) != null) {
            return I18nKey.of(matcher.group(3), matcher.group(1));
        }
        return I18nKey.of(matcher.group(1));
    }

}

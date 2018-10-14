package de.nelius.translation.i18n.variable;

import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Injector for variables in {@link java.util.ResourceBundle} properties.
 * <p>
 * Pattern for variables <b>{variable}</b>
 * Valid chars are 'a-zA-Z0-9-_.'
 *
 * @author Christian Nelius
 */
public class VariableInjector<T> {

    private Pattern pattern = Pattern.compile("\\{([a-zA-Z0-9\\-_.]+)}");
    private Locale locale;
    private Function<VariableQuery<T>, String> variable;

    public VariableInjector(String pattern, Function<VariableQuery<T>, String> variable, Locale locale) {
        this.variable = variable;
        this.locale = locale;
        if (pattern != null && !pattern.trim().isEmpty()) {
            this.pattern = Pattern.compile(pattern);
        }
    }

    public String inject(String text) {
        Matcher matcher = pattern.matcher(text);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, variable.apply(VariableQuery.of(text, matcher.group(1), locale)));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }


}

package de.nelius.i18n;

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
public class VariableInjector {

    private static Pattern pattern = Pattern.compile("\\{([a-zA-Z0-9\\-_.]+)}");

    private String key;
    private Function<String, String> variable;

    public VariableInjector(String key, Function<String, String> variable) {
        this.key = key;
        this.variable = variable;
    }

    public VariableInjector(String key, String pattern, Function<String, String> variable) {
        this.key = key;
        this.variable = variable;
        this.pattern = Pattern.compile(pattern);
    }

    public String inject(String text) {
        Matcher matcher = pattern.matcher(text);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, variable.apply(matcher.group(1)));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }


}
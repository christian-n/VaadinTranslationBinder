package de.nelius.translation.i18n.variable;

import java.util.Locale;

/**
 * Query container for variable needed for injection with {@link VariableInjector}.
 * <p>
 * Property
 *
 * @author Christian Nelius
 */
public class VariableQuery<T> {

    private T target;
    private Locale locale;
    private String property;
    private String variable;

    private VariableQuery() {
    }

    public static VariableQuery of(String property, String variable, Locale locale) {
        VariableQuery variableQuery = new VariableQuery();
        variableQuery.locale = locale;
        variableQuery.property = property;
        variableQuery.variable = variable;
        return variableQuery;
    }

    public VariableQuery<T> with(T target) {
        this.target = target;
        return this;
    }

    public T getTarget() {
        return target;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getProperty() {
        return property;
    }

    public String getVariable() {
        return variable;
    }

    public boolean is(String variable) {
        return this.variable.equals(variable);
    }
}

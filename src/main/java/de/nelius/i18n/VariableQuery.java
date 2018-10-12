package de.nelius.i18n;

/**
 * Query container for variable needed for injection with {@link VariableInjector}.
 *
 * Property
 *
 * @author Christian Nelius
 */
public class VariableQuery<T> {

    private T target;
    private String property;
    private String variable;

    private VariableQuery(String property, String variable) {
        this.property = property;
        this.variable = variable;
    }

    public static VariableQuery of(String property, String variable) {
        return new VariableQuery(property, variable);
    }

    public VariableQuery<T> with(T target) {
        this.target = target;
        return this;
    }

    public T getTarget() {
        return target;
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

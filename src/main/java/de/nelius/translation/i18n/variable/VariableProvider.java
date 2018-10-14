package de.nelius.translation.i18n.variable;

/**
 * Variable provider.
 *
 * @author Christian Nelius
 */
public interface VariableProvider {

    <T> String variable(VariableQuery<T> variableQuery);

}

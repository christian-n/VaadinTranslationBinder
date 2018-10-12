package de.nelius.i18n;

/**
 * Variable provider
 *
 * @author Christian Nelius
 */
public interface VariableProvider {

    <T> String variable(VariableQuery<T> variableQuery);

}

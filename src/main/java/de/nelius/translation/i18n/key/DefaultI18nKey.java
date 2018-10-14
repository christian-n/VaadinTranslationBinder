package de.nelius.translation.i18n.key;

import de.nelius.translation.i18n.variable.VariableQuery;

import java.util.function.Function;


public class DefaultI18nKey<T> implements I18nKey {

    private String key;
    private String variablePattern;
    private String bundle;
    private Function<VariableQuery<T>, String> variableProvider;

    public static DefaultI18nKey of(String key) {
        DefaultI18nKey defaultI18nKey = new DefaultI18nKey();
        defaultI18nKey.key = key;
        return defaultI18nKey;
    }

    public static <T> DefaultI18nKey of(String key, Function<VariableQuery<T>, String> variableProvider) {
        DefaultI18nKey defaultI18nKey = of(key);
        defaultI18nKey.variableProvider = variableProvider;
        return defaultI18nKey;
    }

    public static DefaultI18nKey of(String key, String bundle) {
        DefaultI18nKey defaultI18nKey = of(key);
        defaultI18nKey.bundle = bundle;
        return defaultI18nKey;
    }

    public static <T> DefaultI18nKey of(String key, String bundle, Function<VariableQuery<T>, String> variableProvider) {
        DefaultI18nKey defaultI18nKey = of(key, bundle);
        defaultI18nKey.variableProvider = variableProvider;
        return defaultI18nKey;
    }

    public static <T> DefaultI18nKey of(String key, String bundle, Function<VariableQuery<T>, String> variableProvider, String variablePattern) {
        DefaultI18nKey defaultI18nKey = of(key, bundle, variableProvider);
        defaultI18nKey.variablePattern = variablePattern;
        return defaultI18nKey;
    }

    public static <T> I18nKey<T> withVariableProvider(I18nKey<T> key, Function<VariableQuery<T>, String> variableProvider) {
        return of(key.getKey(), key.getBundle(), variableProvider, key.getVariablePattern());
    }

    public String getKey() {
        return key;
    }

    public Function<VariableQuery<T>, String> getVariableProvider() {
        return variableProvider;
    }

    public String getBundle() {
        return bundle;
    }

    public String getVariablePattern() {
        return variablePattern;
    }
}

package de.nelius.i18n;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;

import com.vaadin.ui.UI;
import javafx.beans.property.ObjectProperty;

public class I18nBinder extends UiBinder {

    private static ObjectProperty<I18nTranslator> translator = Beans.get(I18nTranslator.class);
    private static final String LOCALE = "locale";
    private static UiProvider uiProvider = session ->
    {
        Locale locale = (Locale) session.attribute(LOCALE);
        if (locale == null) {
            locale = session.getRequest().getLocale();
        }
        return locale;
    };

    public static void changeLocale(Locale locale) {
        UiBinder.trigger(Session.get().with(LOCALE, locale));
    }

    public static void bind(String key, Consumer<String> consumer) {
        add(key, consumer);
    }

    public static void bind(String key, Consumer<String> consumer, Function<VariableQuery<String>, String> variableProvider) {
        add(DefaultI18nKey.of(key, variableProvider), consumer);
    }

    public static <T> void bind(I18nKey<T> key, Consumer<String> consumer) {
        bind(key, consumer);
    }

    public static <T> Function<T, String> asFunction(Runnable runnable, Function<T, String> keySupplier) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(DefaultI18nKey.of(keySupplier.apply(s)), localeListener.getLocale());
    }

    public static <T> Function<T, String> asFunction(Runnable runnable, Function<T, String> keySupplier, Function<VariableQuery<T>, String> variableProvider) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(DefaultI18nKey.of(keySupplier.apply(s), new TargetedQuery<>(s, variableProvider)::apply), localeListener.getLocale());
    }

    public static Function<String, String> asStringFunction(Runnable runnable) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(DefaultI18nKey.of(s), localeListener.getLocale());
    }

    public static Function<String, String> asStringFunction(Runnable runnable, Function<VariableQuery<String>, String> variableProvider) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(DefaultI18nKey.of(s, new TargetedQuery<>(s, variableProvider)::apply), localeListener.getLocale());
    }


    public static <T> Function<I18nKey<T>, String> asKeyFunction(Runnable runnable) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(s, localeListener.getLocale());
    }

//    public static <T> Function<I18nKey<T>, String> asKeyFunction(Runnable runnable, Function<VariableQuery<I18nKey<T>>, String> variableProvider) {
//        LocaleListener localeListener = LocaleListener.with(runnable);
//        return s -> translator.get().translate(DefaultI18nKey.of(s, new TargetedQuery<>(s, variableProvider)::apply), localeListener.getLocale());
//    }

    private static void addListener(LocaleListener localeListener, Runnable runnable) {
        add(UiKey.of(UI.getCurrent().getId(), locale -> {
            localeListener.setLocale(locale);
            runnable.run();
        }, Locale.class, uiProvider));
    }

    private static void add(String key, Consumer<String> consumer) {
        add(DefaultI18nKey.of(key), consumer);
    }

    private static void add(DefaultI18nKey key, Consumer<String> consumer) {
        if (!containsProvider(uiProvider)) {
            addProvider(uiProvider);
        }
        add(UiKey.of(UI.getCurrent().getId(), locale -> consumer.accept(translator.get().translate(key, locale)), Locale.class, uiProvider));
    }

    private final static class TargetedQuery<T> {

        private T target;
        private Function<VariableQuery<T>, String> variableProvider;

        public TargetedQuery(T target, Function<VariableQuery<T>, String> variableProvider) {
            this.target = target;
            this.variableProvider = variableProvider;
        }

        public String apply(VariableQuery<T> variableQuery) {
            return variableProvider.apply(variableQuery.with(target));
        }
    }

    private final static class LocaleListener {

        private Locale locale;

        private LocaleListener() {

        }

        public static LocaleListener with(Runnable runnable) {
            LocaleListener localeListener = new LocaleListener();
            addListener(localeListener, runnable);
            return localeListener;
        }

        public void setLocale(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }
    }

}

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

    public static void bind(Consumer<String> consumer) {
//        add(consumer);
    }

    public static void bind(String key, Consumer<String> consumer, Function<String, String> variableProvider) {
        add(I18nKey.of(key, variableProvider), consumer);
    }

    public static Function<Object, String> asFunction(Runnable runnable, Function<Object, String> keySupplier) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(I18nKey.of(keySupplier.apply(s)), localeListener.getLocale());
    }

    public static Function<Object, String> asFunction(Runnable runnable, Function<Object, String> keySupplier, Function<String, String> variableProvider) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(I18nKey.of(keySupplier.apply(s), variableProvider), localeListener.getLocale());
    }

    public static Function<String, String> asStringFunction(Runnable runnable) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(I18nKey.of(s), localeListener.getLocale());
    }

    public static Function<String, String> asStringFunction(Runnable runnable, Function<String, String> variableProvider) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(I18nKey.of(s, variableProvider), localeListener.getLocale());
    }


    public static Function<Enum<?>, String> asEnumFunction(Runnable runnable) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(I18nKey.of(toKey(s)), localeListener.getLocale());
    }

    public static Function<Enum<?>, String> asEnumFunction(Runnable runnable, Function<String, String> variableProvider) {
        LocaleListener localeListener = LocaleListener.with(runnable);
        return s -> translator.get().translate(I18nKey.of(toKey(s), variableProvider), localeListener.getLocale());
    }

    public static void bind(Enum<?> key, Consumer<String> consumer) {
        bind(toKey(key), consumer);
    }

    public static void bind(Enum<?> key, Consumer<String> consumer, Function<String, String> variableProvider) {
        add(I18nKey.of(toKey(key), variableProvider), consumer);
    }

    private static void addListener(LocaleListener localeListener, Runnable runnable) {
        add(UiKey.of(UI.getCurrent().getId(), locale -> {
            localeListener.setLocale(locale);
            runnable.run();
        }, Locale.class));
    }

//    private static String supplier(I18nKey key) {
//        I18nObject object = new I18nObject();
//        add(key, c -> object.setValue(c));
//        return object.getValue();
//    }
//
//    private static String supplier(I18nKey key, Consumer<String> consumer) {
//        I18nObject object = new I18nObject();
//        add(key, c -> {
//            object.setValue(c);
//            consumer.accept(c);
//        });
//        return object.getValue();
//    }

    private static void add(String key, Consumer<String> consumer) {
        add(I18nKey.of(key), consumer);
    }

    private static void add(I18nKey key, Consumer<String> consumer) {
        if (!containsProvider(uiProvider)) {
            addProvider(uiProvider);
        }
        add(UiKey.of(UI.getCurrent().getId(), locale -> consumer.accept(translator.get().translate(key, locale)), Locale.class));
    }

    private static String toKey(Enum<?> key) {
        return key.getClass().getSimpleName().toLowerCase() + "." + key.name().replace("_", ".").toLowerCase();
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

//    private static class I18nObject {
//
//        private String value;
//
//        public void setValue(String value) {
//            this.value = value;
//            notify();
//        }
//
//        public String getValue() {
//            try {
//                wait();
//                return value;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "null";
//        }
//
//    }
}

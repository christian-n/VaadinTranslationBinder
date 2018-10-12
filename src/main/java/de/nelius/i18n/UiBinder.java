package de.nelius.i18n;

import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class UiBinder {


    private static Map<Session, Set<UiKey<?>>> keys = new HashMap<>();
    private static Set<UiProvider> uiProviders = new HashSet<>();
    private static Set<Consumer<Session>> destroyListener = new HashSet<>();

    public static void addDestroyListener(Consumer<Session> listener) {
        destroyListener.add(listener);
    }

    public static void removeDestroyListener(Consumer<Session> listener) {
        destroyListener.remove(listener);
    }

    public static void destroy(Session session) {
        keys.remove(session);
        destroyListener.parallelStream().forEach(consumer -> consumer.accept(session));
    }

    public static void trigger(Session session) {
        if (!keys.containsKey(session)) {
            return;
        }
        Set<UiKey<?>> uiKeys = keys.get(session);
        for (UiProvider provider : uiProviders) {
            Object value = provider.value(session);
            if (value == null) {
                continue;
            }
            for (UiKey uiKey : uiKeys) {
                if (uiKey.supports(value.getClass())) {
                    uiKey.getConsumer().accept(value);
                }
            }
        }
    }

    protected static void add(UiKey<?> key) {
        if (!keys.containsKey(Session.get())) {
            keys.put(Session.get(), new HashSet<>());
        }
        keys.get(Session.get()).add(key);
    }

    protected static void remove(UiKey<?> key) {
        if (!keys.containsKey(Session.get())) {
            keys.put(Session.get(), new HashSet<>());
        }
        keys.get(Session.get()).remove(key);
    }

    protected static void addProvider(UiProvider provider) {
        uiProviders.add(provider);
        provider.value(Session.get());
    }

    protected static boolean containsProvider(UiProvider provider) {
        return uiProviders.contains(provider);
    }

    protected static void removeProvider(UiProvider provider) {
        uiProviders.remove(provider);
    }

}

package de.nelius.translation.ui;

import de.nelius.translation.session.Session;

import java.util.function.Consumer;

public class UiKey<T> {

    private String id;
    private Consumer<T> consumer;
    private Class<T> target;
    private UiProvider<T> provider;

    private UiKey(String id, Consumer<T> consumer, Class<T> target, UiProvider<T> provider) {
        this.id = id;
        this.consumer = consumer;
        this.target = target;
        this.provider = provider;
        consumer.accept(provider.value(Session.get()));
    }

    public static <T> UiKey of(String id, Consumer<T> consumer, Class<T> target, UiProvider<T> provider) {
        return new UiKey(id, consumer, target, provider);
    }

    public String getId() {
        return id;
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }

    public Class<T> getTarget() {
        return target;
    }

    public boolean supports(Class<?> target) {
        return this.target.equals(target);
    }

}

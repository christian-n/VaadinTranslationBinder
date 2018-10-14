package de.nelius.translation.beans;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Property for bean injection. Uses JavaFXs {@link javafx.beans.property.ObjectProperty}.
 */
public class BeanProperty<T> extends SimpleObjectProperty<T> {

    private Class<T> type;
    private boolean required;

    public BeanProperty(Class<T> type) {
        this.type = type;
    }

    public BeanProperty(Class<T> type, boolean required) {
        this.type = type;
        this.required = required;
    }

    public BeanProperty(T bean) {
        if (bean == null) {
            throw new IllegalArgumentException("Bean should not be null!");
        }
        this.type = (Class<T>) bean.getClass();
        set(bean);
    }

    public Class<T> getBeanType() {
        return type;
    }

    public boolean isPresent() {
        return get() != null;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}

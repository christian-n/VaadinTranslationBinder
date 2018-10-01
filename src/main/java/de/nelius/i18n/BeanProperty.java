package de.nelius.i18n;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Property for bean injection. Uses JavaFXs {@link javafx.beans.property.ObjectProperty}.
 */
public class BeanProperty<T> extends SimpleObjectProperty<T> {

    private Class<T> type;

    public BeanProperty(Class<T> type) {
        this.type = type;
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

}

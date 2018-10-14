package de.nelius.translation.beans;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Helper class for static use of springs bean injection.
 *
 * @author Christian Nelius
 */
@Component
public class Beans implements ApplicationListener {

    private static Logger logger = LoggerFactory.getLogger(Beans.class);
    private static Set<BeanProperty> propagated = new HashSet<>();
    private static ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent) {
            applicationContext = ((ApplicationReadyEvent) event).getApplicationContext();
        }
        if (event instanceof ContextRefreshedEvent) {
            ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
            propagated.forEach(p -> set(p, applicationContext));
        }
    }

    private static void set(BeanProperty beanProperty, ApplicationContext context) {
        try {
            beanProperty.set(context.getBean(beanProperty.getBeanType()));
            propagated.remove(beanProperty);
        } catch (NoSuchBeanDefinitionException | BeanCreationException e) {
            if (!beanProperty.isRequired()) {
                return;
            }
            if (!BeanCurrentlyInCreationException.class.isAssignableFrom(e.getClass())) {
                logger.error("Could not provide bean of type [" + beanProperty.getBeanType().getName() + "]!", e);
            }
        }
    }

    public static <T> BeanProperty<T> get(Class<T> beanType) {
        return add(new BeanProperty<>(beanType));
    }

    public static <T> BeanProperty<T> get(Class<T> beanType, boolean required) {
        return add(new BeanProperty<>(beanType, required));
    }

    private static <T> BeanProperty add(BeanProperty<T> beanProperty) {
        propagated.add(beanProperty);
        if (applicationContext != null) {
            set(beanProperty, applicationContext);
        }
        return beanProperty;
    }
}

package de.nelius.i18n;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
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
public class Beans implements ApplicationListener
{

   private static Logger logger = LoggerFactory.getLogger(Beans.class);
   private static Set<BeanProperty> propagated = new HashSet<>();
   private static ApplicationContext applicationContext;

   @Override
   public void onApplicationEvent(ApplicationEvent event)
   {
      if( event instanceof ApplicationReadyEvent )
      {
         applicationContext = ((ApplicationReadyEvent) event).getApplicationContext();
      }
      if( event instanceof ContextRefreshedEvent )
      {
         ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
         propagated.forEach(p -> set(p, applicationContext));
      }
   }

   private static void set(BeanProperty beanProperty, ApplicationContext context)
   {
      try
      {
         beanProperty.set(context.getBean(beanProperty.getBeanType()));
         propagated.remove(beanProperty);
      }
      catch(BeanCreationException e)
      {
         if( !BeanCurrentlyInCreationException.class.isAssignableFrom(e.getClass()) )
         {
            logger.error("Could not provide bean of type [" + beanProperty.getBeanType().getName() + "]!", e);
         }
      }
   }

   public static <T> BeanProperty<T> get(Class<T> beanType)
   {
      BeanProperty<T> beanProperty = new BeanProperty<>(beanType);
      propagated.add(beanProperty);
      if( applicationContext != null )
      {
         set(beanProperty, applicationContext);
      }
      return beanProperty;
   }
}

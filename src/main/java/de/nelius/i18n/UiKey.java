package de.nelius.i18n;

import java.util.function.Consumer;

public class UiKey<T>
{

   private String id;
   private Consumer<T> consumer;
   private Class<T> target;

   private UiKey(String id, Consumer<T> consumer, Class<T> target)
   {
      this.id = id;
      this.consumer = consumer;
      this.target = target;
   }

   public static <T> UiKey of(String id, Consumer<T> consumer, Class<T> target)
   {
      return new UiKey(id, consumer, target);
   }

   public String getId()
   {
      return id;
   }

   public Consumer<T> getConsumer()
   {
      return consumer;
   }

   public Class<T> getTarget()
   {
      return target;
   }

   public boolean supports(Class<?> target)
   {
      return this.target.equals(target);
   }

}

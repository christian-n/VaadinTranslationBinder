package de.nelius.i18n;

import java.util.function.Function;


public class I18nKey
{

   private String key;
   private String bundle;
   private Function<String, String> variableProvider;

   private I18nKey(String key, Function<String, String> variableProvider)
   {
      this.key = key;
      this.variableProvider = variableProvider;
   }

   private I18nKey(String key, String bundle, Function<String, String> variableProvider)
   {
      this.key = key;
      this.variableProvider = variableProvider;
      this.bundle = bundle;
   }

   private I18nKey(String key, String bundle)
   {
      this.key = key;
      this.bundle = bundle;
   }

   private I18nKey(String key)
   {
      this.key = key;
   }

   public static I18nKey of(String key, Function<String, String> variableProvider)
   {
      return new I18nKey(key, variableProvider);
   }

   public static I18nKey of(String key)
   {
      return new I18nKey(key);
   }

   public static I18nKey of(String key, String bundle)
   {
      return new I18nKey(key, bundle);
   }

   public static I18nKey of(String key, String bundle, Function<String, String> variableProvider)
   {
      return new I18nKey(key, bundle, variableProvider);
   }

   public String getKey()
   {
      return key;
   }

   public Function<String, String> getVariableProvider()
   {
      return variableProvider;
   }

   public String getBundle()
   {
      return bundle;
   }
}

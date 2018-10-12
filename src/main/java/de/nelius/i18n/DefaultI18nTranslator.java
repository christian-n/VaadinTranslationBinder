package de.nelius.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.util.StringUtils;

/**
 * Default {@link I18nTranslator} for key injection by {@link KeyInjector} and variable injection by {@link VariableInjector}
 *
 * @author Christian Nelius
 */
public class DefaultI18nTranslator implements I18nTranslator {

    private static Cache<Session, ResourceBundles> bundleCache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build();
    private static final String bundle = "i18n";

    public DefaultI18nTranslator() {
        UiBinder.addDestroyListener(bundleCache::invalidate);
    }

    @Override
    public <T> String translate(I18nKey<T> key, Locale locale) {
        if (key.getVariableProvider() == null) {
            return new KeyInjector(this, locale).inject(rawTranslation(key, locale));
        }
        return new KeyInjector(new VariableInjector(key.getKey(), key.getVariableProvider()), this, locale)
                .inject(rawTranslation(key, locale));
    }

    private <T> String rawTranslation(I18nKey<T> key, Locale locale) {
        if (StringUtils.isEmpty(key.getBundle())) {
            return getBundle(locale).getBundle(bundle).getString(key.getKey());
        }
        return getBundle(locale).getBundle(key.getBundle()).getString(key.getKey());
    }

    private ResourceBundles getBundle(Locale locale) {
        try {
            ResourceBundles bundles = bundleCache.get(Session.get(), () -> new ResourceBundles(locale));
            if (!bundles.equals(new ResourceBundles(locale))) {
                bundleCache.invalidate(Session.get());
                return bundleCache.get(Session.get(), () -> new ResourceBundles(locale));
            }
            return bundles;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ResourceBundles(Locale.ROOT);
    }

    private class ResourceBundles {

        private Locale locale;
        private Map<String, ResourceBundle> bundles = new HashMap<>();

        public ResourceBundles(Locale locale) {
            this.locale = locale;
        }

        public ResourceBundle getBundle(String name) {
            if (!bundles.containsKey(name)) {
                bundles.put(name, ResourceBundle.getBundle(name, locale));
            }
            return bundles.get(name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ResourceBundles that = (ResourceBundles) o;

            return locale != null ? locale.equals(that.locale) : that.locale == null;
        }

        @Override
        public int hashCode() {
            return locale != null ? locale.hashCode() : 0;
        }
    }

}

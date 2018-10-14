package de.nelius.translation.i18n;

import de.nelius.translation.i18n.key.KeyInjector;
import de.nelius.translation.i18n.translator.DefaultI18nTranslator;
import de.nelius.translation.i18n.translator.I18nTranslator;
import de.nelius.translation.i18n.variable.VariableInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Adds necessary beans to {@link I18nBinder}.
 *
 * @author Christian Nelius
 */
@Configuration
public class I18nConfiguration {

    /**
     * {@link I18nTranslator} default implementation. Adds support for {@link KeyInjector} and {@link VariableInjector}.
     * Uses {@link java.util.ResourceBundle} as i18n provider.
     *
     * @return Default {@link I18nTranslator}
     */
    @Bean
    public DefaultI18nTranslator defaultI18nTranslator() {
        return new DefaultI18nTranslator();
    }


}

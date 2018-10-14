package de.nelius.translation.exception;

/**
 * @author Christian Nelius
 */
public class MissingConfigurationException extends RuntimeException {

    public MissingConfigurationException(Class<?> targetType, Class<?> missingType) {
        super(targetType.getSimpleName() + " needs a bean that is missing! Please add bean of type " + missingType + " to the context.");
    }

}

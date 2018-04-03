package by.bsuir.eeb.rsoicoursework.annotation;

import by.bsuir.eeb.rsoicoursework.security.filter.JwtFilter;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Tells {@link JwtFilter} that this resource could be accessed
 * without an authorization token
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface FreeAccess {
}

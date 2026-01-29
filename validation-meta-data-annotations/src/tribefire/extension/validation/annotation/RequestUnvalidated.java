package tribefire.extension.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Marks a request type as explicitly excluded from the validation framework.
 * 
 * <p>
 * When a request class is annotated with @RequestUnvalidated, the validation extension will skip validation entirely for this request, even if
 * validation metadata is present or validation is globally enabled.
 * 
 * <p>
 * This annotation is intended to make the absence of validation an explicit and deliberate design decision, avoiding accidental or implicit behavior.
 * 
 * <p>
 * Typical use cases include technical, internal, legacy, or performance-critical requests where validation would be redundant or undesired.
 * 
 * <p>
 * The {@code globalId} attribute refers to the globalId of the related meta-data to maintain identity.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RequestUnvalidated {
    String globalId() default "";
}
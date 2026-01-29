package tribefire.extension.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Marks a request type as explicitly participating in the validation framework.
 * 
 * <p>
 * When a request class is annotated with @RequestValidated, the validation extension will actively evaluate all applicable validation metadata (e.g.
 * mandatory fields, min/max constraints, custom predicates) before the request is processed.
 * 
 * <p>
 * The {@code globalId} attribute refers to the globalId of the related meta-data to maintain identity.  
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RequestValidated {
	String globalId() default "";
}
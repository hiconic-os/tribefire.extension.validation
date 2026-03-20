package tribefire.extension.validation.annotation.info;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Attaches human-readable validation information.
 *
 * <p>
 * When a property, or request element is annotated with {@code @ValidationInfo}, the provided text describes the expected validation criteria in a form
 * intended for human understanding.
 *
 * <p>
 * This annotation does not define or enforce validation rules. It serves purely as descriptive metadata and must be used in conjunction with actual
 * validation mechanisms.
 *
 * <p>
 * The information may be used by tooling to:
 * <ul>
 * <li>Enrich OpenAPI descriptions with validation guidance</li>
 * <li>Display contextual hints or helper text in user interfaces</li>
 * </ul>
 *
 * <p>
 * Localization is achieved by applying the annotation multiple times, once for each {@link #locale()}.
 * <p>
 * The {@code globalId} attribute refers to the globalId of the related meta-data to maintain identity.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Documented
public @interface ValidationInfo {
    String globalId() default "";
    String locale() default "default";
	String value();
}

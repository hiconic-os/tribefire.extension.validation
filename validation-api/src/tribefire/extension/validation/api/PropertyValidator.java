package tribefire.extension.validation.api;

import java.util.function.Consumer;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.Property;

public interface PropertyValidator<E extends GenericEntity> {
	void validate(ValidationContext context, E entity, Property property, Object value, Consumer<Reason> invalidations);
}

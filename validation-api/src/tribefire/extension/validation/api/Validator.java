package tribefire.extension.validation.api;

import java.util.function.Consumer;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.generic.GenericEntity;

public interface Validator<E extends GenericEntity> {
	void validate(ValidationContext context, E entity, Consumer<Reason> invalidations);
}

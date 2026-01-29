package tribefire.extension.validation.api;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.generic.GenericEntity;

public interface Validator<E extends GenericEntity> {
	Reason validate(ValidationContext context, E entity);
}

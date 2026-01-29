package tribefire.extension.validation.api;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;

public interface ValidatorFactory<E extends GenericEntity> {
	List<Validator<E>> buildValidators(ValidationContext context, EntityType<?> entityType);
}

package tribefire.extension.validation.api;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;

public interface ValidationExperts {
	List<Validator<?>> getValidators(EntityType<?> entityType);
	List<ValidatorFactory<?>> getValidatorFactory(EntityType<?> entityType);
}

package tribefire.extension.validation.api;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;

public interface ValidationExpertsConfigurer {
	<E extends GenericEntity> void registerValidator(EntityType<E> entityType, Validator<E> validator);
	<E extends GenericEntity> void registerValidatorFactory(ValidatorFactory<E> validatorFactory);
}

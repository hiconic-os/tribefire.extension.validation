package tribefire.extension.validation.api;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;

public interface ValidationExpertsConfigurer {
	<E extends GenericEntity> void register(EntityType<E> entityType, Validator<E> validator, boolean polymorph);
	<E extends GenericEntity> void registerValidatorFactory(EntityType<E> entityType, ValidatorFactory<E> validatorFactory, boolean polymorph);
}

package tribefire.extension.validation.processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;

import tribefire.extension.validation.api.ValidationExperts;
import tribefire.extension.validation.api.ValidationExpertsConfigurer;
import tribefire.extension.validation.api.Validator;
import tribefire.extension.validation.api.ValidatorFactory;

public class ConfigurableValidationExperts implements ValidationExperts, ValidationExpertsConfigurer {

	private Map<EntityType<?>,List<Validator<?>>> validators = new ConcurrentHashMap<>();
	private Map<EntityType<?>,List<ValidatorFactory<?>>> validatorFactories = new ConcurrentHashMap<>();
	
	@Override
	public <E extends GenericEntity> void registerValidator(EntityType<E> entityType, Validator<E> validator) {
		validators.compute(entityType, (_,v) -> {
			if (v==null) 
				v = new ArrayList<>();
			
			v.add(validator);
			return v;
		});
	}

	@Override
	public <E extends GenericEntity> void registerValidatorFactory(EntityType<E> entityType, ValidatorFactory<E> validatorFactory) {
		validatorFactories.compute(entityType, (_,v) -> {
			if (v==null) 
				v = new ArrayList<>();
			
			v.add(validatorFactory);
			return v;
		});
		
	}

	@Override
	public List<Validator<?>> getValidators(EntityType<?> entityType) {
		return validators.getOrDefault(entityType, Collections.emptyList());
	}

	@Override
	public List<ValidatorFactory<?>> getValidatorFactory(EntityType<?> entityType) {
		return validatorFactories.getOrDefault(entityType, Collections.emptyList());
	}

}

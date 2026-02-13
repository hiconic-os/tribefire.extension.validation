package tribefire.extension.validation.processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;

import tribefire.extension.validation.api.ValidationExperts;
import tribefire.extension.validation.api.ValidationExpertsConfigurer;
import tribefire.extension.validation.api.Validator;
import tribefire.extension.validation.api.ValidatorFactory;

public class ConfigurableValidationExperts implements ValidationExperts, ValidationExpertsConfigurer {

	private Map<EntityType<?>,List<Validator<?>>> validators = new ConcurrentHashMap<>();
	private Set<ValidatorFactory<?>> validatorFactories = ConcurrentHashMap.newKeySet();
	
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
	public <E extends GenericEntity> void registerValidatorFactory(ValidatorFactory<E> validatorFactory) {
		validatorFactories.add(validatorFactory);
	}

	@Override
	public List<Validator<?>> getValidators(EntityType<?> entityType) {
		return validators.getOrDefault(entityType, Collections.emptyList());
	}

	@Override
	public Iterable<ValidatorFactory<?>> getValidatorFactories() {
		return validatorFactories;
	}
}

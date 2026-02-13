package tribefire.extension.validation.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.meta.cmd.CmdResolver;

import tribefire.extension.validation.api.ValidationExperts;
import tribefire.extension.validation.api.Validator;
import tribefire.extension.validation.api.ValidatorFactory;

public class ContextualizedValidators {
	private CmdResolver mdResolver;
	private ValidationExperts validationExperts;
	private Map<EntityType<?>, List<Validator<?>>> declaredValidators = new ConcurrentHashMap<>();
	private Map<EntityType<?>, List<Validator<?>>> validators = new ConcurrentHashMap<>();
	
	public ContextualizedValidators(CmdResolver mdResolver, ValidationExperts validationExperts) {
		super();
		this.mdResolver = mdResolver;
		this.validationExperts = validationExperts;
	}

	private List<Validator<?>> getDeclaredValidators(EntityType<?> entityType) {
		List<Validator<?>> experts = declaredValidators.get(entityType);
		
		if (experts == null) {
			experts = new ArrayList<>();
			
			experts.addAll(validationExperts.getValidators(entityType));
			
			for (ValidatorFactory<?> factory: validationExperts.getValidatorFactories())
				experts.addAll(factory.buildValidators(mdResolver, entityType));
			
			declaredValidators.put(entityType, experts);
		}
		
		return experts;
	}
	
	public List<Validator<?>> getExperts(EntityType<?> entityType) {
		List<Validator<?>> experts = validators.get(entityType);
		
		if (experts == null) {
			experts = determineExperts(entityType);
			validators.put(entityType, experts);
		}
		
		return experts;
	}

	private List<Validator<?>> determineExperts(EntityType<?> entityType) {
		List<EntityType<?>> superTypes = EntitySuperTypes.getLinearizedSuperTypes(entityType);
		
		List<Validator<?>> effectiveValidators = new ArrayList<Validator<?>>();
		
		for (EntityType<?> superType: superTypes)
			effectiveValidators.addAll(getDeclaredValidators(superType));
		
		return effectiveValidators;
	}
}

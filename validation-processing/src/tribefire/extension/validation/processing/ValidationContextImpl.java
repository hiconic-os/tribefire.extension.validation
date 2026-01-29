package tribefire.extension.validation.processing;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.processing.meta.cmd.CmdResolver;

import tribefire.extension.validation.api.ValidationContext;
import tribefire.extension.validation.api.ValidationExperts;
import tribefire.extension.validation.api.Validator;
import tribefire.extension.validation.api.ValidatorFactory;

public class ValidationContextImpl implements ValidationContext {
	
	private Map<EntityType<?>, List<Validator<?>>> validators = new IdentityHashMap<>();
	
	private CmdResolver mdResolver;

	private ValidationExperts validationExperts;

	private GenericModelType rootType;

	private Object rootValue;
	
	public ValidationContextImpl(CmdResolver mdResolver, ValidationExperts validationExperts, Object rootValue) {
		this(mdResolver, validationExperts, rootValue, GMF.getTypeReflection().getType(rootValue));
	}
	
	public ValidationContextImpl(CmdResolver mdResolver, ValidationExperts validationExperts, Object rootValue, GenericModelType rootType) {
		this.mdResolver = mdResolver;
		this.validationExperts = validationExperts;
		this.rootValue = rootValue;
		this.rootType = rootType;
	}
	
	@Override
	public CmdResolver mdResolver() {
		return mdResolver;
	}
	
	@Override
	public GenericModelType getRootType() {
		return rootType;
	}
	
	@Override
	public Object getRootValue() {
		return rootValue;
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
		List<Validator<?>> validators = new ArrayList<>();

		for (EntityType<?> superType : entityType.getSuperTypes()) {
			validators.addAll(getExperts(entityType));
		}

		for (ValidatorFactory<?> validatorFactory : validationExperts.getValidatorFactory(entityType)) {
			validators.addAll(validatorFactory.buildValidators(this, entityType));
		}
		
		validators.addAll(validationExperts.getValidators(entityType));
		
		return validators;
	}
}

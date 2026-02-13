package tribefire.extension.validation.processing;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.processing.meta.cmd.CmdResolver;

import tribefire.extension.validation.api.ValidationContext;

public class ValidationContextImpl implements ValidationContext {
	
	
	private CmdResolver mdResolver;

	private ContextualizedValidators validators;

	private GenericModelType rootType;

	private Object rootValue;
	
	public ValidationContextImpl(CmdResolver mdResolver, ContextualizedValidators validators, Object rootValue) {
		this(mdResolver, validators, rootValue, GMF.getTypeReflection().getType(rootValue));
	}
	
	public ValidationContextImpl(CmdResolver mdResolver, ContextualizedValidators validators, Object rootValue, GenericModelType rootType) {
		this.mdResolver = mdResolver;
		this.rootValue = rootValue;
		this.rootType = rootType;
		
		this.validators = validators; 
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
	
	public ContextualizedValidators getValidators() {
		return validators;
	}
}

package tribefire.extension.validation.processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.meta.data.constraint.Mandatory;
import com.braintribe.model.meta.data.constraint.Max;
import com.braintribe.model.meta.data.constraint.MaxLength;
import com.braintribe.model.meta.data.constraint.Min;
import com.braintribe.model.meta.data.constraint.MinLength;
import com.braintribe.model.meta.data.constraint.Pattern;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.meta.cmd.builders.EntityMdResolver;
import com.braintribe.model.processing.meta.cmd.builders.PropertyMdResolver;

import tribefire.extension.validation.api.PropertyValidator;
import tribefire.extension.validation.api.ValidationContext;
import tribefire.extension.validation.api.Validator;
import tribefire.extension.validation.api.ValidatorFactory;
import tribefire.extension.validation.model.reason.MandatoryViolation;
import tribefire.extension.validation.model.reason.MaxLengthViolation;
import tribefire.extension.validation.model.reason.MaxViolation;
import tribefire.extension.validation.model.reason.MinLengthViolation;
import tribefire.extension.validation.model.reason.MinViolation;
import tribefire.extension.validation.model.reason.PatternViolation;
import tribefire.extension.validation.model.reason.PropertyViolation;

public class MetadataValidatorFactory implements ValidatorFactory<GenericEntity> {
	@Override
	public List<Validator<GenericEntity>> buildValidators(ValidationContext context, EntityType<?> entityType) {
		CmdResolver mdResolver = context.mdResolver();
		EntityMdResolver entityMdResolver = mdResolver.getMetaData().entityType(entityType);
		
		List<PropertyValidation> validations = new ArrayList<>();
		
		for (Property property : entityType.getDeclaredProperties()) {
			PropertyMdResolver propertyMdResolver = entityMdResolver.property(property);
			
			if (propertyMdResolver.is(Mandatory.T))
				validations.add(new PropertyValidation(property, this::validateMandatory));
			
			Pattern pattern = propertyMdResolver.meta(Pattern.T).exclusive();
			
			if (pattern != null)
				validations.add(new PropertyValidation(property, new PatternValidator(pattern.getExpression())));
			
			MaxLength maxLength = propertyMdResolver.meta(MaxLength.T).exclusive();
			
			if (maxLength != null)
				validations.add(new PropertyValidation(property, new MaxLenValidator(maxLength.getLength())));
			
			MinLength minLength = propertyMdResolver.meta(MinLength.T).exclusive();
			
			if (minLength != null)
				validations.add(new PropertyValidation(property, new MinLenValidator(minLength.getLength())));
			
			Max max = propertyMdResolver.meta(Max.T).exclusive();
			
			if (max != null && max.getLimit() instanceof Number)
				validations.add(new PropertyValidation(property, new MaxValidator((Number)max.getLimit(), max.getExclusive())));
			
			Min min = propertyMdResolver.meta(Min.T).exclusive();

			if (min != null && min.getLimit() instanceof Number)
				validations.add(new PropertyValidation(property, new MinValidator((Number)min.getLimit(), min.getExclusive())));
		}
		
		if (validations.isEmpty())
			return Collections.emptyList();
		
		return Collections.singletonList(new MetadataPropertyValidator(validations));
	}
		
	private Reason validateMandatory(ValidationContext context, GenericEntity entity, Property property, Object propertyValue) {
		if (propertyValue != null)
			return null;
		
		return Reasons.build(MandatoryViolation.T).text("Property value must not be null").toReason();
	}
	
	private record MaxLenValidator(long len) implements PropertyValidator<GenericEntity> {
		@Override
		public Reason validate(ValidationContext context, GenericEntity entity, Property property, Object propertyValue) {
			if (propertyValue instanceof String) {
				String propertyStringValue = (String) propertyValue;

				int length = propertyStringValue.length();
				if (len < length) 
					return Reasons.build(MaxLengthViolation.T) //
							.text("String has " + length + " is longer than its allowed maximum length: " + len) //
							.toReason();
				
			}
			
			return null;
		}
	}
	
	private record MinLenValidator(long len) implements PropertyValidator<GenericEntity> {
		@Override
		public Reason validate(ValidationContext context, GenericEntity entity, Property property, Object propertyValue) {
			if (propertyValue instanceof String) {
				String propertyStringValue = (String) propertyValue;

				int length = propertyStringValue.length();

				if (len > length)
					return Reasons.build(MinLengthViolation.T) //
							.text("String has " + length + " chars and is shorter than its allowed minimum length: " + len) //
							.toReason();
			}
			
			return null;
		}
	}
	
	private class MaxValidator implements PropertyValidator<GenericEntity> {
		private Comparable<Object> limit;
		private boolean exclusive;
		
		MaxValidator(Number limit, boolean exclusive) {
			this.exclusive = exclusive;
			this.limit = (Comparable<Object>)limit;
		}
		
		@Override
		public Reason validate(ValidationContext context, GenericEntity entity, Property property, Object propertyValue) {
			if (propertyValue == null)
				return null;
			
			if (propertyValue.getClass() != limit.getClass())
				return null;
			
			boolean violated = exclusive?
					limit.compareTo(propertyValue) <= 0:
					limit.compareTo(propertyValue) < 0;

			if (!violated)
				return null;

			return Reasons.build(MaxViolation.T).text("Property exceeds its allowed maximum value. Max: " + limit).toReason();
		}
	}
	
	private class MinValidator implements PropertyValidator<GenericEntity> {
		private Comparable<Object> limit;
		private boolean exclusive;
		
		MinValidator(Number limit, boolean exclusive) {
			this.exclusive = exclusive;
			this.limit = (Comparable<Object>)limit;
		}
		
		@Override
		public Reason validate(ValidationContext context, GenericEntity entity, Property property, Object propertyValue) {
			if (propertyValue == null)
				return null;
			
			if (propertyValue.getClass() != limit.getClass())
				return null;
			
			boolean violated = exclusive?
					limit.compareTo(propertyValue) >= 0:
					limit.compareTo(propertyValue) > 0;

			if (!violated)
				return null;

			return Reasons.build(MinViolation.T).text("Property exceeds its allowed minimum value. Min: " + limit).toReason();
		}
	}

	private class PatternValidator implements PropertyValidator<GenericEntity> {
		private java.util.regex.Pattern pattern;
		
		PatternValidator(String expr) {
			pattern = java.util.regex.Pattern.compile(expr);
		}
		
		@Override
		public Reason validate(ValidationContext context, GenericEntity entity, Property property, Object value) {
			if (value == null || pattern.matcher(value.toString()).matches())
				return null;
			
			return Reasons.build(PatternViolation.T).text("Value must comply the regex pattern: " + pattern.pattern()).toReason();
		}
	}
	
	
	private record PropertyValidation(Property property, PropertyValidator<GenericEntity> propertyValidator) {
		
	}
	
	private record MetadataPropertyValidator(List<PropertyValidation> validations) implements Validator<GenericEntity> {
		public Reason validate(ValidationContext context, GenericEntity entity) {
			PropertyViolation propertyConstraintViolation = null;
			
			for (var validation: validations) {
				PropertyValidator<GenericEntity> validator = validation.propertyValidator();
				Property property = validation.property();
				Object value = property.get(entity);
				Reason reason = validator.validate(context, entity, property, value);
				
				if (reason == null)
					continue;
				
				if (propertyConstraintViolation == null)
					propertyConstraintViolation = Reasons.build(PropertyViolation.T) //
					.text("Property " +  property.getName() + " has violated constraints") //
					.assign(PropertyViolation::setProperty, property.getName()).toReason();
				
				propertyConstraintViolation.getReasons().add(reason);
			}
			
			return propertyConstraintViolation;
		}
	}
}

package tribefire.extension.validation.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
import tribefire.extension.validation.api.UmbrellaReasoning;
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
	public List<Validator<GenericEntity>> buildValidators(CmdResolver mdResolver, EntityType<?> entityType) {
		EntityMdResolver entityMdResolver = mdResolver.getMetaData().entityType(entityType);
		
		List<Validator<GenericEntity>> validators = new ArrayList<>();
		
		for (Property property : entityType.getDeclaredProperties()) {
			List<PropertyValidator<GenericEntity>> propertyValidators = new ArrayList<>();

			PropertyMdResolver propertyMdResolver = entityMdResolver.property(property);
			
			if (propertyMdResolver.is(Mandatory.T))
				propertyValidators.add(this::validateMandatory);
			
			Pattern pattern = propertyMdResolver.meta(Pattern.T).exclusive();
			
			if (pattern != null)
				propertyValidators.add(new PatternValidator(pattern.getExpression()));
			
			MaxLength maxLength = propertyMdResolver.meta(MaxLength.T).exclusive();
			
			if (maxLength != null)
				propertyValidators.add(new MaxLenValidator(maxLength.getLength()));
			
			MinLength minLength = propertyMdResolver.meta(MinLength.T).exclusive();
			
			if (minLength != null)
				propertyValidators.add(new MinLenValidator(minLength.getLength()));
			
			Max max = propertyMdResolver.meta(Max.T).exclusive();
			
			if (max != null && max.getLimit() instanceof Number)
				propertyValidators.add(new MaxValidator((Number)max.getLimit(), max.getExclusive()));
			
			Min min = propertyMdResolver.meta(Min.T).exclusive();

			if (min != null && min.getLimit() instanceof Number)
				propertyValidators.add(new MinValidator((Number)min.getLimit(), min.getExclusive()));
			
			if (!propertyValidators.isEmpty())
				validators.add(new MetadataPropertyValidator(property, propertyValidators));
		}
		
		return validators;
	}
		
	private void validateMandatory(ValidationContext context, GenericEntity entity, Property property, Object propertyValue, Consumer<Reason> invalidations) {
		if (propertyValue != null)
			return;
		
		invalidations.accept(Reasons.build(MandatoryViolation.T).text("Property value must not be null").toReason());
	}
	
	private record MaxLenValidator(long len) implements PropertyValidator<GenericEntity> {
		@Override
		public void validate(ValidationContext context, GenericEntity entity, Property property, Object propertyValue, Consumer<Reason> invalidations) {
			if (propertyValue instanceof String) {
				String propertyStringValue = (String) propertyValue;

				int length = propertyStringValue.length();
				if (len < length) 
					invalidations.accept(Reasons.build(MaxLengthViolation.T) //
							.text("String has " + length + " is longer than its allowed maximum length: " + len) //
							.toReason());
				
			}
			
			return;
		}
	}
	
	private record MinLenValidator(long len) implements PropertyValidator<GenericEntity> {
		@Override
		public void validate(ValidationContext context, GenericEntity entity, Property property, Object propertyValue, Consumer<Reason> invalidations) {
			if (propertyValue instanceof String) {
				String propertyStringValue = (String) propertyValue;

				int length = propertyStringValue.length();

				if (len > length)
					invalidations.accept(Reasons.build(MinLengthViolation.T) //
							.text("String has " + length + " chars and is shorter than its allowed minimum length: " + len) //
							.toReason());
			}
			
			return;
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
		public void validate(ValidationContext context, GenericEntity entity, Property property, Object propertyValue, Consumer<Reason> invalidations) {
			if (propertyValue == null)
				return;
			
			if (propertyValue.getClass() != limit.getClass())
				return;
			
			boolean violated = exclusive?
					limit.compareTo(propertyValue) <= 0:
					limit.compareTo(propertyValue) < 0;

			if (!violated)
				return;

			invalidations.accept(Reasons.build(MaxViolation.T).text("Property exceeds its allowed maximum value. Max: " + limit).toReason());
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
		public void validate(ValidationContext context, GenericEntity entity, Property property, Object propertyValue, Consumer<Reason> invalidations) {
			if (propertyValue == null)
				return;
			
			if (propertyValue.getClass() != limit.getClass())
				return;
			
			boolean violated = exclusive?
					limit.compareTo(propertyValue) >= 0:
					limit.compareTo(propertyValue) > 0;

			if (!violated)
				return;

			invalidations.accept(Reasons.build(MinViolation.T).text("Property exceeds its allowed minimum value. Min: " + limit).toReason());
		}
	}

	private class PatternValidator implements PropertyValidator<GenericEntity> {
		private java.util.regex.Pattern pattern;
		
		PatternValidator(String expr) {
			pattern = java.util.regex.Pattern.compile(expr);
		}
		
		@Override
		public void validate(ValidationContext context, GenericEntity entity, Property property, Object value, Consumer<Reason> invalidations) {
			if (value == null || pattern.matcher(value.toString()).matches())
				return;
			
			invalidations.accept(Reasons.build(PatternViolation.T).text("Value must comply the regex pattern: " + pattern.pattern()).toReason());
		}
	}
	
	
	private record MetadataPropertyValidator(Property property, List<PropertyValidator<GenericEntity>> validators) implements Validator<GenericEntity> {
		
		public void validate(ValidationContext context, GenericEntity entity, Consumer<Reason> invalidations) {
			var umbrellaReasoning = UmbrellaReasoning.create(PropertyViolation.T, r -> {
				r.setText("Property " +  property.getName() + " has violated constraints");
				r.setProperty(property.getName());
			});
			
			for (var validator: validators) {
				Object value = property.get(entity);
				validator.validate(context, entity, property, value, umbrellaReasoning);
			}
			
			umbrellaReasoning.forwardIfReasonable(invalidations);
		}
	}
}

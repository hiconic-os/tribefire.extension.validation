package tribefire.extension.validation.model.reason;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ConditionalPropertyViolation extends PropertyViolation {
	EntityType<ConditionalPropertyViolation> T = EntityTypes.T(ConditionalPropertyViolation.class);
	
	String conditionProperty = "conditionProperty";
	String condition = "condition";
	
	String getConditionProperty();
	void setConditionProperty(String conditionProperty);
	
	Object getCondition();
	void setCondition(Object condition);
	
}

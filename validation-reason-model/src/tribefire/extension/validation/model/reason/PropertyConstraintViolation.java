package tribefire.extension.validation.model.reason;

import com.braintribe.gm.model.reason.essential.InvalidArgument;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface PropertyConstraintViolation extends InvalidArgument {
	EntityType<PropertyConstraintViolation> T = EntityTypes.T(PropertyConstraintViolation.class);
	
	String getProperty();
	void setProperty(String property);
}

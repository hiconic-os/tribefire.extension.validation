package tribefire.extension.validation.model.reason;

import com.braintribe.gm.model.reason.essential.InvalidArgument;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface PropertyViolation extends InvalidArgument {
	EntityType<PropertyViolation> T = EntityTypes.T(PropertyViolation.class);
	
	String getProperty();
	void setProperty(String property);
}

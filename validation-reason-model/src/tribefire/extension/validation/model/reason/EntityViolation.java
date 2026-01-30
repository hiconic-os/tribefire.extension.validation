package tribefire.extension.validation.model.reason;

import com.braintribe.gm.model.reason.essential.InvalidArgument;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface EntityViolation extends InvalidArgument {
	EntityType<EntityViolation> T = EntityTypes.T(EntityViolation.class);
	
	String getTypeSignature();
	void setTypeSignature(String typeSignature);
	
	String getPath();
	void setPath(String path);
}

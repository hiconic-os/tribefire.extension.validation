package tribefire.extension.validation.model.reason;

import com.braintribe.gm.model.reason.essential.InvalidArgument;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface EntityConstraintViolation extends InvalidArgument {
	EntityType<EntityConstraintViolation> T = EntityTypes.T(EntityConstraintViolation.class);
	
	String getTypeSignature();
	void setTypeSignature(String typeSignature);
	
	String getPath();
	void setPath(String path);
}

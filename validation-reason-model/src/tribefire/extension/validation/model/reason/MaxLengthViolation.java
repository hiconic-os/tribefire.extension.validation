package tribefire.extension.validation.model.reason;

import com.braintribe.gm.model.reason.essential.InvalidArgument;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface MaxLengthViolation extends InvalidArgument {
	EntityType<MaxLengthViolation> T = EntityTypes.T(MaxLengthViolation.class);
}

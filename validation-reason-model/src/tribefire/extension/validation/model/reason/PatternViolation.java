package tribefire.extension.validation.model.reason;

import com.braintribe.gm.model.reason.essential.InvalidArgument;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface PatternViolation extends InvalidArgument {
	EntityType<PatternViolation> T = EntityTypes.T(PatternViolation.class);
}

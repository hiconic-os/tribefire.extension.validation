package tribefire.extension.validation.model.deployment;

import com.braintribe.model.extensiondeployment.ServiceAroundProcessor;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ValidationAroundProcessor extends ServiceAroundProcessor {

	EntityType<ValidationAroundProcessor> T = EntityTypes.T(ValidationAroundProcessor.class);
}

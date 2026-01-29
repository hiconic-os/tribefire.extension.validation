package tribefire.extension.validation.model.meta;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.data.EntityTypeMetaData;
import com.braintribe.model.meta.data.ExplicitPredicate;

/**
 * <p>
 * Meta-data indicating that a request type explicitly participates in the validation framework.
 * 
 * <p>
 * This meta-data is derived from the presence of the {@code @RequestValidated} annotation and is exposed via the entity type system to allow
 * validation decisions to be made at runtime.
 * 
 * <p>
 * By implementing {@code ExplicitPredicate}, this meta-data represents a clear boolean signal: if present, validation is explicitly enabled for the
 * associated request type.
 * 
 * <p>
 * This separation between annotation and meta-data avoids direct annotation inspection at runtime and allows validation logic to integrate seamlessl
 * with interceptors and other infrastructure components.
 */
public interface RequestValidated extends EntityTypeMetaData, ExplicitPredicate {

	EntityType<RequestValidated> T = EntityTypes.T(RequestValidated.class);
	
}

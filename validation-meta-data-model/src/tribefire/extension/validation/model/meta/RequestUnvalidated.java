package tribefire.extension.validation.model.meta;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.data.PredicateErasure;

/**
 * <p>
 * Meta-data indicating that a request type is explicitly excluded from the validation framework.
 * 
 * <p>
 * This meta-data is associated with request entity types and is typically derived from the presence of the {@code @RequestUnvalidated} annotation.
 * 
 * <p>
 * By implementing {@code ExplicitPredicate}, this meta-data represents an explicit, negative validation intent: if present on a request type,
 * validation is considered disabled for that type.
 * 
 * <p>
 * The meta-data is exposed via the entity type system to allow validation decisions to be made at runtime without relying on direct annotation
 * inspection.
 * 
 * <p>
 * The {@code globalId} is used to maintain identity and stable references between annotations, meta-data, and external configuration or tooling.
 */
public interface RequestUnvalidated extends RequestValidated, PredicateErasure {

	EntityType<RequestUnvalidated> T = EntityTypes.T(RequestUnvalidated.class);
	
}

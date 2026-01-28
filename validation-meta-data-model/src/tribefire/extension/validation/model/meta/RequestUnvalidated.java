package tribefire.extension.validation.model.meta;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.data.PredicateErasure;

public interface RequestUnvalidated extends RequestValidated, PredicateErasure {

	EntityType<RequestUnvalidated> T = EntityTypes.T(RequestUnvalidated.class);
}

package tribefire.extension.validation.model.meta;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.data.EntityTypeMetaData;
import com.braintribe.model.meta.data.ExplicitPredicate;

public interface RequestValidated extends EntityTypeMetaData, ExplicitPredicate {

	EntityType<RequestValidated> T = EntityTypes.T(RequestValidated.class);
}

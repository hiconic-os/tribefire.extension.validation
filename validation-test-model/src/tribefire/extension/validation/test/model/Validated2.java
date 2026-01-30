package tribefire.extension.validation.test.model;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface Validated2 extends Validated {

	EntityType<Validated2> T = EntityTypes.T(Validated2.class);

}

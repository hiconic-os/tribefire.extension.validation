package tribefire.extension.validation.test.model;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface Validated1 extends Validated {

	EntityType<Validated1> T = EntityTypes.T(Validated1.class);

	String info = "info";
	
	Validated2 getInfo();
	void setInfo(Validated2 info);
}

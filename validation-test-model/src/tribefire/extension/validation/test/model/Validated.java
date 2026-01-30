package tribefire.extension.validation.test.model;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.meta.Max;
import com.braintribe.model.generic.annotation.meta.MaxLength;
import com.braintribe.model.generic.annotation.meta.Min;
import com.braintribe.model.generic.annotation.meta.MinLength;
import com.braintribe.model.generic.annotation.meta.Pattern;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface Validated extends GenericEntity {

	EntityType<Validated> T = EntityTypes.T(Validated.class);
	
	String name = "name";
	String dependency = "dependency";
	String number1 = "number1";
	String number2 = "number2";
	
	@MaxLength(10)
	@MinLength(3)
	String getName();
	void setName(String name);
	
	@Pattern("([a-zA-Z0-9._-]+):([a-zA-Z0-9._-]+)#([a-zA-Z0-9._-]+)")
	String getDependency();
	void setDependency(String dependency);
	
	@Min("3")
	@Max("10")
	int getNumber1();
	void setNumber1(int number1);
	
	@Min(value = "3", exclusive = true)
	@Max(value = "10", exclusive = true)
	int getNumber2();
	void setNumber2(int number2);

}

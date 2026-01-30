package tribefire.extension.validation.test.model;

import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.service.api.result.Neutral;

import tribefire.extension.validation.annotation.RequestValidated;

@RequestValidated
public interface ValidateMe extends ServiceRequest {

	EntityType<ValidateMe> T = EntityTypes.T(ValidateMe.class);
	
	String payload = "payload";
	
	@Mandatory
	Validated1 getPayload();
	void setPayload(Validated1 payload);

	@Override
	EvalContext<Neutral> eval(Evaluator<ServiceRequest> evaluator);
}

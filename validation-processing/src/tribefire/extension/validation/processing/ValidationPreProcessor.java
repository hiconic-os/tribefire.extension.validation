package tribefire.extension.validation.processing;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.braintribe.cfg.Required;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.Reason;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.service.api.ReasonedServicePreProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.validation.api.ValidationExperts;
import tribefire.extension.validation.model.meta.RequestValidated;

public class ValidationPreProcessor implements ReasonedServicePreProcessor<ServiceRequest> {
	private static Logger logger = Logger.getLogger(ValidationPreProcessor.class);
	private Function<String, CmdResolver> mdResolverLookup;
	private ValidationExperts validationExperts;
	private Map<CmdResolver, ContextualizedValidators> contextualizedValidators = new ConcurrentHashMap<>();
	
	@Required
	public void setMdResolverLookup(Function<String, CmdResolver> mdResolverLookup) {
		this.mdResolverLookup = mdResolverLookup;
	}
	
	@Required
	public void setValidationExperts(ValidationExperts validationExperts) {
		this.validationExperts = validationExperts;
	}
	
	@Override
	public Maybe<? extends ServiceRequest> processReasoned(ServiceRequestContext requestContext,
			ServiceRequest request) {
		
		String domainId = requestContext.getDomainId();
		
		CmdResolver mdResolver = mdResolverLookup.apply(domainId);
		
		boolean validated = mdResolver.getMetaData().entity(request).is(RequestValidated.T);
		
		if (!validated)
			return Maybe.complete(request);
		
		logger.debug("Validating request of type " + request.entityType().getTypeSignature());
		
		Validation validation = new Validation(mdResolver, getValidators(mdResolver));
		
		Reason violation = validation.validate(request, request.entityType());
		
		if (violation == null)
			return Maybe.complete(request);
		

		logger.info("Invalidated request of type " + request.entityType().getTypeSignature() + ": " + violation.stringify());
		
		return violation.asMaybe();
	}
	
	private ContextualizedValidators getValidators(CmdResolver mdResolver) {
		return contextualizedValidators.computeIfAbsent(mdResolver, r -> new ContextualizedValidators(r, validationExperts));
		
	}
}

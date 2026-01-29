package tribefire.extension.validation.wire.space;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import hiconic.rx.module.api.service.ModelConfiguration;
import hiconic.rx.module.api.service.ModelConfigurations;
import hiconic.rx.module.api.service.ServiceDomains;
import hiconic.rx.module.api.wire.RxModuleContract;
import hiconic.rx.module.api.wire.RxPlatformContract;
import tribefire.extension.validation._ValidationConfiguredApiModel_;
import tribefire.extension.validation.api.ValidationExpertsConfigurer;
import tribefire.extension.validation.api.wire.contract.ValidationModuleContract;
import tribefire.extension.validation.processing.ConfigurableValidationExperts;
import tribefire.extension.validation.processing.MetadataValidatorFactory;
import tribefire.extension.validation.processing.ValidationPreProcessor;

@Managed
public class ValidationRxModuleSpace implements RxModuleContract, ValidationModuleContract {

	@Import
	private RxPlatformContract platform;

	@Override
	public void configureModels(ModelConfigurations configurations) {
		ModelConfiguration configuration = configurations.byName(_ValidationConfiguredApiModel_.name);
		configuration.bindInterceptor("service-auth").forType(ServiceRequest.T).bind(this::validationPreProcessor);
	}
	
	@Override
	public ValidationExpertsConfigurer validationExpertsConfigurer() {
		return validationExperts();
	}
	
	@Managed
	private ConfigurableValidationExperts validationExperts() {
		ConfigurableValidationExperts bean = new ConfigurableValidationExperts();
		bean.registerValidatorFactory(GenericEntity.T, metadataValidatorFactory());
		return bean;
	}
	
	@Managed
	private MetadataValidatorFactory metadataValidatorFactory() {
		return new MetadataValidatorFactory();
	}
	
	@Managed
	private ValidationPreProcessor validationPreProcessor() {
		ValidationPreProcessor bean = new ValidationPreProcessor();
		ServiceDomains serviceDomains = platform.serviceDomains();
		
		bean.setMdResolverLookup(domainId -> serviceDomains.byId(domainId).contextCmdResolver());
		bean.setValidationExperts(validationExperts());
		return bean;
	}

}
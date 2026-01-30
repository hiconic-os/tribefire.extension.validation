package tribefire.extension.validation.test.wire.space;

import com.braintribe.model.service.api.result.Neutral;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import hiconic.rx.module.api.service.ServiceDomainConfiguration;
import hiconic.rx.module.api.wire.RxModuleContract;
import hiconic.rx.module.api.wire.RxPlatformConfigurator;
import hiconic.rx.module.api.wire.RxPlatformContract;
import tribefire.extension.validation._ValidationConfiguredApiModel_;
import tribefire.extension.validation.api.wire.contract.ValidationModuleContract;
import tribefire.extension.validation.test.model.ValidateMe;
import tribefire.extension.validation.test.model.Validated1;

@Managed
public class ValidationRxModuleTestSpace implements RxModuleContract {

	@Import
	private RxPlatformContract platform;

	@Import
	private ValidationModuleContract validation;
	
	@Override
	public void configureMainServiceDomain(ServiceDomainConfiguration configuration) {
		configuration.addModel(platform.configuredModels().byName(_ValidationConfiguredApiModel_.name));
		configuration.bindRequest(ValidateMe.T, () -> (_,_) -> Neutral.NEUTRAL);
	}
	
	@Override
	public void configurePlatform(RxPlatformConfigurator configurator) {
		validation.validationExpertsConfigurer().registerValidator(Validated1.T, validated1Validator());
	}
	
	@Managed
	private Validated1Validator validated1Validator() {
		return new Validated1Validator();
	}
}
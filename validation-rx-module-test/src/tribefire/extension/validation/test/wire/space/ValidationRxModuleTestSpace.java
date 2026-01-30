package tribefire.extension.validation.test.wire.space;

import com.braintribe.model.service.api.result.Neutral;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import hiconic.rx.module.api.service.ServiceDomainConfiguration;
import hiconic.rx.module.api.wire.RxModuleContract;
import hiconic.rx.module.api.wire.RxPlatformContract;
import tribefire.extension.validation._ValidationConfiguredApiModel_;
import tribefire.extension.validation.test.model.ValidateMe;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class ValidationRxModuleTestSpace implements RxModuleContract {

	@Import
	private RxPlatformContract platform;

	@Override
	public void configureMainServiceDomain(ServiceDomainConfiguration configuration) {
		configuration.addModel(platform.configuredModels().byName(_ValidationConfiguredApiModel_.name));
		configuration.bindRequest(ValidateMe.T, () -> (_,_) -> Neutral.NEUTRAL);
	}
}
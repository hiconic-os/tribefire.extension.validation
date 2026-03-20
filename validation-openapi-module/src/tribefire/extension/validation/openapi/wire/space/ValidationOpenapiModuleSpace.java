
package tribefire.extension.validation.openapi.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.validation.openapi.processing.ValidationOpenapiDescriptionResolver;
import tribefire.extension.webapi.openapi_v3.api.wire.contract.OpenapiV3ModuleContract;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class ValidationOpenapiModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;
	
	@Import
	private OpenapiV3ModuleContract openapi;

	@Override
	public void bindHardwired() {
		openapi.descriptionResolverRegistry().registerDescriptionResolver("validation", descriptionResolver());
	}

	@Managed
	private ValidationOpenapiDescriptionResolver descriptionResolver() {
		return new ValidationOpenapiDescriptionResolver();
	}

}
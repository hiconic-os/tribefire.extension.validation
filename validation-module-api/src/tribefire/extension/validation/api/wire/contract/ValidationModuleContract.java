package tribefire.extension.validation.api.wire.contract;

import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.validation.api.ValidationExpertsConfigurer;

public interface ValidationModuleContract extends WireSpace {

	ValidationExpertsConfigurer validationExpertsConfigurer();
	
}

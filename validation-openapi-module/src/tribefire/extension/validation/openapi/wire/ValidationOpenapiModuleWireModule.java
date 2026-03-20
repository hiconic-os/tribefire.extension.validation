
package tribefire.extension.validation.openapi.wire;

import tribefire.module.wire.contract.StandardTribefireModuleWireModule;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.extension.validation.openapi.wire.space.ValidationOpenapiModuleSpace;

public enum ValidationOpenapiModuleWireModule implements StandardTribefireModuleWireModule {

	INSTANCE;

	@Override
	public Class<? extends TribefireModuleContract> moduleSpaceClass() {
		return ValidationOpenapiModuleSpace.class;
	}

}

package tribefire.extension.validation.initializer.wire;

import static com.braintribe.wire.api.util.Lists.list;

import java.util.List;

import com.braintribe.wire.api.module.WireModule;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.integrity.wire.CoreInstancesWireModule;

import tribefire.extension.validation.initializer.wire.contract.ValidationInitializerContract;

public enum ValidationInitializerWireModule implements WireTerminalModule<ValidationInitializerContract> {

	INSTANCE;

	@Override
	public List<WireModule> dependencies() {
		return list(CoreInstancesWireModule.INSTANCE);
	}

}

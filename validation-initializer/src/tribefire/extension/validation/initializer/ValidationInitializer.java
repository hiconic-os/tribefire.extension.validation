
package tribefire.extension.validation.initializer;

import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;

import tribefire.extension.validation.initializer.wire.ValidationInitializerWireModule;
import tribefire.extension.validation.initializer.wire.contract.ValidationInitializerContract;

public class ValidationInitializer extends AbstractInitializer<ValidationInitializerContract> {

	@Override
	public WireTerminalModule<ValidationInitializerContract> getInitializerWireModule() {
		return ValidationInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<ValidationInitializerContract> initializerContext,
			ValidationInitializerContract initializerContract) {

		initializerContract.initialize();

	}

}
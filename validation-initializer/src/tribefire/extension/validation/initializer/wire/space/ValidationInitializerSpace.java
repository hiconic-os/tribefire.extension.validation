
package tribefire.extension.validation.initializer.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;

import tribefire.extension.validation.initializer.wire.contract.ValidationInitializerContract;
import tribefire.extension.validation.initializer.wire.contract.ValidationLookupContract;

@Managed
public class ValidationInitializerSpace extends AbstractInitializerSpace implements ValidationInitializerContract {

	@Import
	private ValidationLookupContract validationLookup;

	@Import
	private CoreInstancesContract coreInstances;

	/* To ensure beans are initialized simply reference them here (i.e. invoke their defining methods).  */
	@Override
	public void initialize() {

	}

}

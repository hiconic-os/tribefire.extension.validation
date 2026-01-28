
package tribefire.extension.validation.initializer.wire.contract;

import com.braintribe.wire.api.space.WireSpace;
import tribefire.extension.validation.initializer.wire.space.ValidationInitializerSpace;

public interface ValidationInitializerContract extends WireSpace {

	/** @see ValidationInitializerSpace#initialize()  */
	void initialize();

}


package tribefire.extension.validation.initializer.wire.contract;

import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.impl.lookup.GlobalId;
import tribefire.cortex.initializer.support.impl.lookup.InstanceLookup;

@InstanceLookup(lookupOnly = true)
public interface ValidationLookupContract extends WireSpace {

	String GROUP_ID = "tribefire.extension.validation";

//  EXAMPLE:
//
//	@GlobalId("model:com.braintribe.gm:root-model")
//	GmMetaModel rootModel();
//
//  MAKE SURE TO IMPORT [tribefire.cortex.initializer.support.impl.lookup.GlobalId]

}

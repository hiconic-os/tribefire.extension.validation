package tribefire.extension.validation.wire;

import hiconic.rx.module.api.wire.Exports;
import hiconic.rx.module.api.wire.RxModule;
import tribefire.extension.validation.api.wire.contract.ValidationModuleContract;
import tribefire.extension.validation.wire.space.ValidationRxModuleSpace;

public enum ValidationRxModule implements RxModule<ValidationRxModuleSpace> {

	INSTANCE;
	
	@Override
	public void bindExports(Exports exports) {
		exports.bind(ValidationModuleContract.class, ValidationRxModuleSpace.class);
	}

}
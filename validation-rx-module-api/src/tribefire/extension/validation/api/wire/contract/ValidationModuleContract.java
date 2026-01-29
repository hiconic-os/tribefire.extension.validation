package tribefire.extension.validation.api.wire.contract;

import hiconic.rx.module.api.wire.RxExportContract;
import tribefire.extension.validation.api.ValidationExpertsConfigurer;

public interface ValidationModuleContract extends RxExportContract {

	ValidationExpertsConfigurer validationExpertsConfigurer();
	
}

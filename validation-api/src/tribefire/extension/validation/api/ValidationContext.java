package tribefire.extension.validation.api;

import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.processing.meta.cmd.CmdResolver;

public interface ValidationContext {
	CmdResolver mdResolver();
	Object getRootValue();
	GenericModelType getRootType();
}

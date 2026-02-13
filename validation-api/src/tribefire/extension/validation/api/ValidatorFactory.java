package tribefire.extension.validation.api;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.meta.cmd.CmdResolver;

public interface ValidatorFactory<E extends GenericEntity> {
	List<Validator<E>> buildValidators(CmdResolver mdResolver, EntityType<?> entityType);
}

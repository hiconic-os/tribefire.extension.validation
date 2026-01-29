package tribefire.extension.validation.api;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.processing.traversing.api.path.TraversingModelPathElement;

public record ConstraintValidation(TraversingModelPathElement modelPathElement, Reason reason) {

}

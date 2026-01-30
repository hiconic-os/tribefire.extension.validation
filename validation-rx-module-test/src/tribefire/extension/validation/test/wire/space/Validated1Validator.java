package tribefire.extension.validation.test.wire.space;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.gm.model.reason.Reasons;

import tribefire.extension.validation.api.ValidationContext;
import tribefire.extension.validation.api.Validator;
import tribefire.extension.validation.model.reason.ConditionalPropertyViolation;
import tribefire.extension.validation.model.reason.ValueViolation;
import tribefire.extension.validation.test.model.Validated1;

/**
 * This test validator checks whether a given {@code name} AND {@code number1} matches. <br/>
 * If not, the validator returns a {@code PropertyConstraintViolation}.
 */
public class Validated1Validator implements Validator<Validated1> {

	@Override
	public Reason validate(ValidationContext context, Validated1 entity) {
		String name = entity.getName();
		
		String condition = "Zirkel";
		if (name.equals(condition) && entity.getNumber3() != 42) {
			return Reasons.build(ConditionalPropertyViolation.T) //
					.text("Invalid conditional property " + Validated1.number3 + " for name " + condition) //
					.assign(ConditionalPropertyViolation::setConditionProperty, Validated1.name) //
					.assign(ConditionalPropertyViolation::setCondition, condition) //
					.assign(ConditionalPropertyViolation::setProperty, Validated1.number3) //
					.cause(Reasons.build(ValueViolation.T).text("Value must be 42").toReason())
					.toReason();
		}
		
		// everything is fine, the universe is in sync, cause of 42
		return null;
	}

}

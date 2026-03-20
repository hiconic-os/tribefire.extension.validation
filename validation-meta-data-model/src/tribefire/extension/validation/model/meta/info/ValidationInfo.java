package tribefire.extension.validation.model.meta.info;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.data.UniversalMetaData;

/**
 * Human-readable information describing validation expectations.
 * 
 * <p>
 * This metadata is intended for documentation and UI purposes only. It provides guidance to users, but does not represent or enforce the actual
 * validation logic.
 * 
 * <p>
 * The actual validation rules must be defined and enforced separately.
 * 
 * <p>
 * Typical use cases:
 * <ul>
 *   <li>Displaying hints or helper text in user interfaces</li>
 *   <li>Enriching API documentation with human-readable validation guidance</li>
 * </ul>
 */
public interface ValidationInfo extends UniversalMetaData {

	EntityType<ValidationInfo> T = EntityTypes.T(ValidationInfo.class);
	
	String text = "text";

	void setText(String text);
	String getText();
	
}

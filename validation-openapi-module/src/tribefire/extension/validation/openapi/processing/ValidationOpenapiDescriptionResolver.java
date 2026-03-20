package tribefire.extension.validation.openapi.processing;

import java.util.List;
import java.util.function.Consumer;

import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.model.processing.meta.cmd.builders.EntityMdResolver;
import com.braintribe.model.processing.meta.cmd.builders.MdResolver;
import com.braintribe.model.processing.meta.cmd.builders.ModelMdResolver;
import com.braintribe.model.processing.meta.cmd.builders.PropertyMdResolver;
import com.braintribe.utils.DOMTools;

import tribefire.extension.validation.model.meta.info.ValidationInfo;
import tribefire.extension.webapi.openapi_v3.api.OpenapiDescriptionResolver;

public class ValidationOpenapiDescriptionResolver implements OpenapiDescriptionResolver {
	@Override
	public void resolveEntityDescription(ModelMdResolver modelMdResolver, EntityMdResolver entityMdResolver, Consumer<String> consumer) {
		resolveDescription(entityMdResolver, consumer);
	}

	@Override
	public void resolvePropertyDescription(ModelMdResolver modelMdResolver, PropertyMdResolver mdResolver, Consumer<String> consumer) {
		resolveDescription(mdResolver, consumer);
	}
	
	private void resolveDescription(MdResolver<?> mdResolver, Consumer<String> consumer) {
		List<ValidationInfo> validationInfos = mdResolver.meta(ValidationInfo.T).list();
		
		if (validationInfos.isEmpty())
			return;
		
		consumer.accept("<p>\n");
		consumer.accept("<b>Validation Info:</b><br>\n");
		consumer.accept("<ul>\n");
		for (ValidationInfo validationInfo: validationInfos) {
			LocalizedString text = validationInfo.getText();
			if (text == null)
				continue;
			
			String value = text.value();
			
			consumer.accept("<li>\n");
			consumer.accept(DOMTools.encode(value));
			consumer.accept("</li>\n");
		}
		consumer.accept("</ul>\n");
		consumer.accept("</p>\n");
	}
}

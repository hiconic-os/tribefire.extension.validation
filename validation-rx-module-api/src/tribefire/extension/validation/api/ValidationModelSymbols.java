package tribefire.extension.validation.api;

import hiconic.rx.module.api.service.ModelSymbol;

/** Identity for the platform-owned validation configuration, independent of the packaged CX model. */
public interface ValidationModelSymbols {
    ModelSymbol configuredValidationApiModel = ModelSymbol.of("tribefire.extension.validation:configured-validation-api-model");
}

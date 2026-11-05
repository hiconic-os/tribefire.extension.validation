# Hiconic Validation Extension

<b>A generic interceptor-based validation framework that enforces metadata constraints such as mandatory fields and min/max values.</b>
<hr>

## Features

* Generic, metadata-driven validation
* Interceptor-based (non-invasive)
* Supports common constraints (mandatory, min, max, …)

## Contributing

<b>Contributions are welcome and encouraged.</b>



## Rx model configuration

Rx consumers add `ValidationModelSymbols.configuredValidationApiModel` from
`validation-rx-module-api`. The module contributes the service API and validation reason
schemas as dependencies, then places validation interceptor metadata in the platform-owned
model's `typeOverrides`. The packaged `validation-configured-api-model` remains for CX.
Do not use its artifact reflection as the Rx configuration identity: reflection models are
shared across platform instances in the same JVM.

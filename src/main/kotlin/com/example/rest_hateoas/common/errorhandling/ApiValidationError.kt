package com.example.rest_hateoas.common.errorhandling

internal data class ApiValidationError(
    val `object`: String,
    val field: String? = null,
    val rejectedValue: Any? = null,
    val message: String,
) : ApiError.ApiSubError()

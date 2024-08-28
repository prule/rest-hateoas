package com.example.rest_hateoas.application.domain.model

import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
@Embeddable
class PersonName(
    @field:NotBlank
    var firstName: String,

    @field:NotBlank
    var lastName: String,

    var otherNames: String? = null
)


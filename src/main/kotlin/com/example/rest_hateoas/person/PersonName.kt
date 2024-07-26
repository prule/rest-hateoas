package com.example.rest_hateoas.person

import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
@Embeddable
class PersonName(
    @NotBlank
    var firstName: String? = null,

    @NotBlank
    var lastName: String? = null,
    var otherNames: String? = null
)


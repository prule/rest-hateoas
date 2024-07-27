package com.example.rest_hateoas.person

import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
@Embeddable
class PersonName(
    @field:NotBlank
    var firstName: String? = null,

    @field:NotBlank
    var lastName: String? = null,
    var otherNames: String? = null
)


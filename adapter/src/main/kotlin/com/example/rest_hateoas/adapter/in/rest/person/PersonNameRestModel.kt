package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.domain.model.PersonName
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
class PersonNameRestModel(
    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    val otherNames: String? = null
) {
    companion object {
        fun fromDomain(value: PersonName): PersonNameRestModel {
            return PersonNameRestModel(
                value.firstName,
                value.lastName,
                value.otherNames
            )
        }

        fun toDomain(value: PersonNameRestModel): PersonName {
            return PersonName(
                value.firstName,
                value.lastName,
                value.otherNames
            )
        }

        fun empty(): PersonNameRestModel {
            return PersonNameRestModel(
                "",
                "",
                ""
            )
        }
    }
}


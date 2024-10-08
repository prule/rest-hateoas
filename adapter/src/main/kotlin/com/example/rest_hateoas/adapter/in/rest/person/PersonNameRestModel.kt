package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.domain.model.PersonName
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
class PersonNameRestModel(

    @field:NotBlank
    var firstName: String,

    @field:NotBlank
    var lastName: String,

    var otherNames: String? = null

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


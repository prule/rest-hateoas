package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.adapter.`in`.rest.support.http.LocalDateSerializer
import jakarta.validation.Valid
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
open class PersonUpdateRestModel(
    var version: Long,

    @field:Valid var name: PersonNameRestModel,
    @field:Valid var address: PersonAddressRestModel,

    @Serializable(with = LocalDateSerializer::class)
    var dateOfBirth: LocalDate? = null

) {

    companion object {
        fun empty(): PersonUpdateRestModel {
            return PersonUpdateRestModel(
                0,
                PersonNameRestModel.empty(),
                PersonAddressRestModel.empty(),
                null
            )
        }
    }
}


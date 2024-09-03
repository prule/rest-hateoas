package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.adapter.`in`.rest.support.http.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
open class PersonCreateRestModel(

    var name: PersonNameRestModel,
    var address: PersonAddressRestModel,

    @Serializable(with = LocalDateSerializer::class)
    var dateOfBirth: LocalDate? = null

) {

    companion object {
        fun empty(): PersonCreateRestModel {
            return PersonCreateRestModel(
                PersonNameRestModel.empty(),
                PersonAddressRestModel.empty(),
                null
            )
        }
    }
}


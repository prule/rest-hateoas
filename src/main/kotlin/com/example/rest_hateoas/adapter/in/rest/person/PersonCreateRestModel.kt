package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.common.LocalDateSerializer
import com.example.rest_hateoas.common.VersionedRepresentationModel
import kotlinx.serialization.Serializable
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
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


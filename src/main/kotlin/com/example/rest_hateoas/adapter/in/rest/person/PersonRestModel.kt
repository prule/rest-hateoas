package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.common.LocalDateSerializer
import com.example.rest_hateoas.common.VersionedRepresentationModel
import kotlinx.serialization.Serializable
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import java.time.LocalDate

@Serializable
open class PersonRestModel(
    override var version: Long? = 0,
    var id: Long?,

    var key: String? = null,

    var name: PersonNameRestModel,
    var address: PersonAddressRestModel,

    @Serializable(with = LocalDateSerializer::class)
    var dateOfBirth: LocalDate? = null

) : VersionedRepresentationModel<PersonRestModel>() {

    companion object {

        fun empty(): PersonRestModel {
            return PersonRestModel(
                0,
                0,
                null,
                PersonNameRestModel.empty(),
                PersonAddressRestModel.empty(),
                null
            )
        }
    }
}

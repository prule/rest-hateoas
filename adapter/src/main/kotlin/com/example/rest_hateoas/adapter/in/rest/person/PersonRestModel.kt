package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.adapter.`in`.rest.support.http.LocalDateSerializer
import com.example.rest_hateoas.adapter.`in`.rest.support.http.VersionedRepresentationModel
import kotlinx.serialization.Serializable
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDate

@Serializable
open class PersonRestModel(
    var version: Long? = 0,

    var key: String? = null,

    var name: PersonNameRestModel,
    var address: PersonAddressRestModel,

    @Serializable(with = LocalDateSerializer::class)
    var dateOfBirth: LocalDate? = null

) : RepresentationModel<PersonRestModel>() {

    companion object {

        fun empty(): PersonRestModel {
            return PersonRestModel(
                0,
                null,
                PersonNameRestModel.empty(),
                PersonAddressRestModel.empty(),
                null
            )
        }
    }
}

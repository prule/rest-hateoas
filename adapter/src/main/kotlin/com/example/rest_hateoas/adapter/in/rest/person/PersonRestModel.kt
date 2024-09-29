package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.adapter.`in`.rest.ModelMetadataRestModel
import com.example.rest_hateoas.adapter.`in`.rest.support.http.LocalDateSerializer
import jakarta.validation.Valid
import kotlinx.serialization.Serializable
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDate

/**
 * In our rest model, we don't expose the internal IDs, only the KEY.
 */
@Serializable
open class PersonRestModel(
    var version: Long = 0,
    var key: String,

    @field:Valid var name: PersonNameRestModel,
    @field:Valid var address: PersonAddressRestModel,

    @Serializable(with = LocalDateSerializer::class)
    var dateOfBirth: LocalDate? = null,

    var metadata: ModelMetadataRestModel

) : RepresentationModel<PersonRestModel>() {

    companion object {

        fun empty(): PersonRestModel {
            return PersonRestModel(
                0,
                "",
                PersonNameRestModel.empty(),
                PersonAddressRestModel.empty(),
                null,
                ModelMetadataRestModel.empty()
            )
        }
    }
}

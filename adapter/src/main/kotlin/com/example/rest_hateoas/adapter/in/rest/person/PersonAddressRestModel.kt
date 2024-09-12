package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.domain.model.PersonAddress
import kotlinx.serialization.Serializable

@Serializable
class PersonAddressRestModel(
    var line1: String? = null,
    var line2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var postcode: String? = null // zipcode etc
) {
    companion object {
        fun fromDomain(value: PersonAddress): PersonAddressRestModel {
            return PersonAddressRestModel(
                value.line1 ,
                value.line2,
                value.city,
                value.state,
                value.country,
                value.postcode
            )
        }

        fun toDomain(value: PersonAddressRestModel): PersonAddress {
            return PersonAddress(
                value.line1,
                value.line2,
                value.city,
                value.state,
                value.country,
                value.postcode
            )
        }

        fun empty(): PersonAddressRestModel {
            return PersonAddressRestModel(
                "",
                "",
                "",
                "",
                "",
                ""
            )
        }
    }
}


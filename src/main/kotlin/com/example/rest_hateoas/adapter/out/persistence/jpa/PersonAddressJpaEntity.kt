package com.example.rest_hateoas.adapter.out.persistence.jpa

import jakarta.persistence.Embeddable
import kotlinx.serialization.Serializable

@Embeddable
class PersonAddressJpaEntity(
    var line1: String? = null,
    var line2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var postcode: String? = null // zipcode etc
) {
}
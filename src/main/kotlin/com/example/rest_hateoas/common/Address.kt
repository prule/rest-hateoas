package com.example.rest_hateoas.common

import jakarta.persistence.Embeddable
import kotlinx.serialization.Serializable

@Serializable
@Embeddable
class Address(
    var line1: String? = null,
    var line2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var postcode: String? = null // zipcode etc
)
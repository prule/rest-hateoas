package com.example.rest_hateoas.domain.model


data class PersonAddress(
    val line1: String? = null,
    val line2: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val postcode: String? = null // zipcode etc
)
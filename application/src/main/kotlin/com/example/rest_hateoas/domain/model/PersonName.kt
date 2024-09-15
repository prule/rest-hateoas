package com.example.rest_hateoas.domain.model


data class PersonName(
    val firstName: String,

    val lastName: String,

    val otherNames: String? = null
)


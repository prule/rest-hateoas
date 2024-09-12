package com.example.rest_hateoas.adapter.`in`.rest.support.authentication

import org.springframework.hateoas.RepresentationModel


open class AuthenticationResponseModel(
    val username: String,
    val token: String
)

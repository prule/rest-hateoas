package com.example.rest_hateoas.adapter.`in`.rest.support.authentication

import org.springframework.hateoas.RepresentationModel


data class AuthenticationRequestModel(
    val username: String,
    val password: String
)

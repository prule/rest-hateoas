package com.example.rest_hateoas.authentication

import org.springframework.hateoas.RepresentationModel


open class AuthenticationResponseModel(
    val username: String,
    val token: String
) : RepresentationModel<AuthenticationResponseModel>() {

}

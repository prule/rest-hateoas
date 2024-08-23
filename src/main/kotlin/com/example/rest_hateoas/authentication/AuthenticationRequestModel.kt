package com.example.rest_hateoas.authentication

import org.springframework.hateoas.RepresentationModel


class AuthenticationRequestModel(
    val username: String,
    val password: String
) : RepresentationModel<AuthenticationRequestModel>() {

}

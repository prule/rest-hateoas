package com.example.rest_hateoas.authentication

import org.springframework.hateoas.RepresentationModel


class LoginRequestModel(
    val username: String,
    val password: String
) : RepresentationModel<LoginRequestModel>() {

}

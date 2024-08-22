package com.example.rest_hateoas.user

import org.springframework.hateoas.RepresentationModel


class LoginRequestModel(
    var username: String,
    val password: String
) : RepresentationModel<LoginRequestModel>() {

}

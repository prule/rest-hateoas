package com.example.rest_hateoas.user

import org.springframework.hateoas.RepresentationModel


class LoginResponseModel(
    var username: String,
    val token: String
) : RepresentationModel<LoginResponseModel>() {

}

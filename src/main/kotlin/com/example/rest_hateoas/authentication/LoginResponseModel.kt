package com.example.rest_hateoas.authentication

import org.springframework.hateoas.RepresentationModel


open class LoginResponseModel(
    val username: String,
    val token: String
) : RepresentationModel<LoginResponseModel>() {

}

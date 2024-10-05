package com.example.rest_hateoas.adapter.`in`.rest.user

class UserCreateRestModel(
    val username: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean = false,
)
package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.domain.model.UserGroup

class UserUpdateRestModel(
    val username: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean = false,
    val roles: List<UserGroupRestModel> = listOf(),
)

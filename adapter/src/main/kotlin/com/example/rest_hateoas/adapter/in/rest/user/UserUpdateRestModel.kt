package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.domain.model.UserGroup

class UserUpdateRestModel(
    var version: Long,

    val username: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean = false,
)

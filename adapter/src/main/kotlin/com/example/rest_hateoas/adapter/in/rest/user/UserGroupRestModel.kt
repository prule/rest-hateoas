package com.example.rest_hateoas.adapter.`in`.rest.user

import kotlinx.serialization.Serializable

@Serializable
class UserGroupRestModel(
    val name: String,
    val description: String,
    val enabled: Boolean,
)
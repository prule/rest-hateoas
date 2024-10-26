package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.domain.model.UserGroup
import kotlinx.serialization.Serializable

@Serializable
class UserGroupUpdateRestModel(
    val name: String,
    val description: String,
    val enabled: Boolean = false,
)

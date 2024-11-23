package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.domain.model.UserGroup
import kotlinx.serialization.Serializable

@Serializable
class UserGroupRestModel(
    val name: UserGroup.Name,
    val description: UserGroup.Description,
    val enabled: UserGroup.Enabled,
)
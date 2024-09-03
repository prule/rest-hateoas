package com.example.rest_hateoas.application.domain.model

import kotlinx.serialization.Serializable

@Serializable
class UserGroup(
    val id: Long? = null,
    val key: Key,
    var name: String,
    var description: String,
    var enabled: Boolean,
)
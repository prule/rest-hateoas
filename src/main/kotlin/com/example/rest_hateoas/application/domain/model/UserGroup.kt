package com.example.rest_hateoas.application.domain.model

import com.example.rest_hateoas.common.Key
import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Serializable
class UserGroup(
    val id: Long? = null,
    val key: Key,
    var name: String,
    var description: String,
    var enabled: Boolean,
)
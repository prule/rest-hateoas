package com.example.rest_hateoas.domain.model


class UserGroup(
    val id: Long? = null,
    val key: Key,
    var name: String,
    var description: String,
    var enabled: Boolean,
)
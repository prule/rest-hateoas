package com.example.rest_hateoas.application.domain.model

import kotlinx.serialization.Serializable

@Serializable
class User (

    val key: Key,
    var username: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var enabled: Boolean,
    var groups: List<UserGroup>,
    var id: Long? = null

    ) {

    fun hasGroup(group: String): Boolean {
        return groups.find { it.name == group } != null
    }

}
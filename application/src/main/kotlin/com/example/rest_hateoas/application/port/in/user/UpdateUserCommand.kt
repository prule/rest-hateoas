package com.example.rest_hateoas.application.port.`in`.user

import com.example.rest_hateoas.domain.model.User

class UpdateUserCommand(
    val user: User,
    val newValues: UpdateUserFields
) {
    data class UpdateUserFields(
        var username: String,
        var firstName: String,
        var lastName: String,
        var enabled: Boolean,
        var version: Long
    )
}
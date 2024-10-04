package com.example.rest_hateoas.application.port.`in`.user

import com.example.rest_hateoas.application.port.ValidatableCommand
import com.example.rest_hateoas.domain.model.User
import com.example.rest_hateoas.domain.model.UserGroup

data class RemoveUserGroupCommand(
    val user: User,
    val group: UserGroup
): ValidatableCommand() {
    init {
        // user must already have this group
        check(user.hasGroup(group)) {
            "User ${user} does not have this group ${group}"
        }
        validate()
    }
}

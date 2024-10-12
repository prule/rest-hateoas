package com.example.rest_hateoas.application.port.`in`.usergroup

import com.example.rest_hateoas.application.port.ValidatableCommand
import com.example.rest_hateoas.domain.model.User
import com.example.rest_hateoas.domain.model.UserGroup

data class AddUserGroupCommand(
    val user: User,
    val group: UserGroup
): ValidatableCommand() {
    init {
        // user cannot already have this group
        check(!user.hasGroup(group)) {
            "User ${user} already has group ${group}"
        }
        validate()
    }
}
